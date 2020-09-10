package qiaodan.yu.tomatomvp.base

import android.content.Context

import com.fly.tomato.common.BaseApplication
import com.fly.tomato.common.http.RxHttpUtils
import com.fly.tomato.common.http.config.OkHttpConfig

class App: BaseApplication() {
    companion object{
        val instance:App
        get()= baseInstance as App

        val context:Context
        get() = BaseApplication.context
    }

    override fun onCreate() {
        super.onCreate()
        initHttp()
    }


   private fun initHttp(){
        val okHttpClient= OkHttpConfig.Builder(this).setDebug(true).build()
        RxHttpUtils
            .getInstance()
            .init(this)
            .config()
            .setBaseUrl("https://api.github.com/")
            .setOkClient(okHttpClient)
    }
}