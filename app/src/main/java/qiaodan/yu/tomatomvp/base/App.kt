package qiaodan.yu.tomatomvp.base

import android.content.Context
import com.fly.tomato.common.BaseApplication

class App : BaseApplication() {
    companion object {
        val instance: App
            get() = baseInstance as App

        val context: Context
            get() = BaseApplication.context
    }

    override fun onCreate() {
        super.onCreate()

    }


}