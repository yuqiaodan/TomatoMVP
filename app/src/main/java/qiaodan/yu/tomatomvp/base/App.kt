package qiaodan.yu.tomatomvp.base

import com.allen.library.RxHttpUtils
import com.allen.library.config.OkHttpConfig
import com.fly.tour.common.BaseApplication

class App: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initHttp()
    }


    fun initHttp(){
        val okHttpClient=OkHttpConfig.Builder(this).setDebug(true).build()
        RxHttpUtils
            .getInstance()
            .init(this)
            .config()
            .setBaseUrl("https://api.github.com/")
            .setOkClient(okHttpClient)
    }
}