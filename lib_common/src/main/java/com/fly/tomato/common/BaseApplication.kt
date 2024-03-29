package com.fly.tomato.common

import android.app.Application
import android.content.Context



import java.lang.Exception

/**
 * Description: <初始化应用程序><br>
 * Author:      mxdl<br>
 * Date:        2018/6/6<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</初始化应用程序> */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: BaseApplication? = null
        val baseInstance: BaseApplication
            get() = instance!!
        val context: Context
            get() = baseInstance.applicationContext
    }

    init {
        instance = this
    }

    open val versionName: String
        get() =
            try {
                packageManager.getPackageInfo(packageName, 0).versionName
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
    open val versionCode: Int
        get() =
            try {
                packageManager.getPackageInfo(packageName, 0).versionCode
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
}
