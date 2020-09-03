package com.fly.tour.common

import android.app.Application


import com.facebook.stetho.Stetho
import com.fly.tour.common.util.log.KLog

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

        KLog.init(BuildConfig.IS_DEBUG)
        Stetho.initializeWithDefaults(this)

    }

    companion object {
        var instance: BaseApplication? = null
            private set
    }
}
