package com.fly.tomato.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fly.tomato.common.manager.ActivityManager


/**
 * Description: <BaseActivity><br>
 * Author:      mxdl<br>
 * Date:        2018/1/16<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseActivity> */
abstract class BaseActivity : AppCompatActivity(){


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
