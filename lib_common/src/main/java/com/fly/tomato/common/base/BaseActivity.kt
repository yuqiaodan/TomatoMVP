package com.fly.tomato.common.base

import android.os.Bundle
import com.fly.tomato.common.manager.ActivityManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/**
 * Description: <BaseActivity><br>
 * Author:      mxdl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseActivity> */
abstract class BaseActivity : RxAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(onBindLayout())
        ActivityManager.getInstance()?.addActivity(this)
    }



    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.getInstance()?.finishActivity(this)
    }

    abstract fun onBindLayout(): Int
}
