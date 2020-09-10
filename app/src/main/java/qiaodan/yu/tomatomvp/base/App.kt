package qiaodan.yu.tomatomvp.base

import android.content.Context
import com.allen.library.RxHttpUtils
import com.allen.library.config.OkHttpConfig
import com.fly.tour.common.BaseApplication

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
        val okHttpClient=OkHttpConfig.Builder(this).setDebug(true).build()
        RxHttpUtils
            .getInstance()
            .init(this)
            .config()
            .setBaseUrl("https://api.github.com/")
            .setOkClient(okHttpClient)
    }
}