package com.fly.tomato.common.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.fly.tomato.common.BaseApplication

@SuppressLint("MissingPermission")
class WifiTools {
    //位置权限！！
    companion object {
        val instance: WifiTools by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { WifiTools() }
    }

    private val TAG = "wifi操作"//网络名称
    private val context = BaseApplication.context
    private val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @SuppressLint("MissingPermission")
    fun connectWifi(ssid: String, password: String) {
        openWifi()
        val scanResult = wifiManager.scanResults.singleOrNull { it.SSID == ssid }
        if (scanResult == null) {
            //wifi不在范围内
           // Toast.makeText(context, context.getString(R.string.search_wifi_fail), Toast.LENGTH_SHORT).show()
            return
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                connectByP2P(ssid, password)
                return
            }
            var isSuccess = false
            //如果找到了wifi了，从配置表中搜索该wifi的配置config，也就是以前有没有连接过
            //注意configuredNetworks中的ssid，系统源码中加上了双引号，这里比对的时候要去掉
            val config =
                wifiManager.configuredNetworks.singleOrNull { it.SSID.replace("\"", "") == ssid }
            isSuccess = if (config != null) {
                //如果找到了，那么直接连接，不要调用wifiManager.addNetwork  这个方法会更改config的！
                wifiManager.enableNetwork(config.networkId, true)
            } else {
                // 没找到的话，就创建一个新的配置，然后正常的addNetWork、enableNetwork即可
                val padWifiNetwork =
                    createWifiConfig(
                        scanResult.SSID,
                        password,
                        getCipherType(scanResult.capabilities)
                    )
                val netId = wifiManager.addNetwork(padWifiNetwork)
                wifiManager.enableNetwork(netId, true)
            }
            if (isSuccess) {
                //连接成功
            } else {
                //连接失败
            }

        }
    }

    private fun openWifi() {
        if (!wifiManager.isWifiEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //请用户手动打开wifi
                //这里可以使用event bus代替 在activity接收到后 打开wifi的设置界面
            } else {
                wifiManager.isWifiEnabled = true
            }
        }
    }

    private fun startScantWifi() {
        val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(TAG, "Wifi扫描完成")
                val results = wifiManager.scanResults//结果

            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
        wifiManager.startScan()
    }

    //Android8以下 通过Config连接Wifi
    private fun connectByConfig() {

    }

    //Android10以上 通过P2P连接Wifi
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectByP2P(ssid: String, password: String) {
        val specifier = WifiNetworkSpecifier.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .build()
        val request =
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .setNetworkSpecifier(specifier)
                .build()

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                //连接成功
            }

            override fun onUnavailable() {
                //连接失败
            }
        }

        connectivityManager.requestNetwork(request, networkCallback)

    }

    //Android10以上，通过suggestion连接WIFI
    private fun connectBySug(ssid: String, password: String) {
        val suggestion = WifiNetworkSuggestion.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .setIsAppInteractionRequired(true) // Optional (Needs location permission)
            .build()
        val suggestionsList = listOf(suggestion)
        //wifiManager.removeNetworkSuggestions(suggestionsList)
        val status = wifiManager.addNetworkSuggestions(suggestionsList)
        Log.d(TAG, status.toString())
        if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {

        }
        val intentFilter = IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION);
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (!intent.action.equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                    return
                }
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter);

    }


    private fun createWifiConfig(
        ssid: String,
        password: String,
        type: WifiCapability
    ): WifiConfiguration {
        //初始化WifiConfiguration
        val config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()

        //指定对应的SSID
        config.SSID = "\"" + ssid + "\""

        //如果之前有类似的配置
        val tempConfig = wifiManager.configuredNetworks.singleOrNull { it.SSID == "\"$ssid\"" }
        if (tempConfig != null) {
            //则清除旧有配置  不是自己创建的network 这里其实是删不掉的
            wifiManager.removeNetwork(tempConfig.networkId)
            wifiManager.saveConfiguration()
        }

        //不需要密码的场景
        if (type == WifiCapability.WIFI_CIPHER_NO_PASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            //以WEP加密的场景
        } else if (type == WifiCapability.WIFI_CIPHER_WEP) {
            config.hiddenSSID = true
            config.wepKeys[0] = "\"" + password + "\""
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
            //以WPA加密的场景，自己测试时，发现热点以WPA2建立时，同样可以用这种配置连接
        } else if (type == WifiCapability.WIFI_CIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\""
            config.hiddenSSID = true
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            config.status = WifiConfiguration.Status.ENABLED
        }

        return config
    }

    private fun getCipherType(capabilities: String): WifiCapability {
        return when {
            capabilities.contains("WEB") -> {
                WifiCapability.WIFI_CIPHER_WEP
            }
            capabilities.contains("PSK") -> {
                WifiCapability.WIFI_CIPHER_WPA
            }
            capabilities.contains("WPS") -> {
                WifiCapability.WIFI_CIPHER_NO_PASS
            }
            else -> {
                WifiCapability.WIFI_CIPHER_NO_PASS
            }
        }
    }
}

enum class WifiCapability {
    WIFI_CIPHER_WEP, WIFI_CIPHER_WPA, WIFI_CIPHER_NO_PASS
}

