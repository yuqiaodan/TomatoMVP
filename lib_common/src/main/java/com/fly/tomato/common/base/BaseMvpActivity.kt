package com.fly.tomato.common.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle

import com.fly.tomato.common.mvp.BaseModel
import com.fly.tomato.common.mvp.BasePresenter

/**
 * Description: <BaseMvpActivity><br>
 * Author:      yuqiaodan
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
abstract class BaseMvpActivity<M : BaseModel, V, P : BasePresenter<M, V>> : BaseActivity() {
    protected var mPresenter: P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter?.attach(this as V)
        mPresenter?.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)

    }

    abstract fun initPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onStart()
    }
    override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
    }



    override fun onPause() {
        super.onPause()
        mPresenter?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        mPresenter?.onRestart()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mPresenter?.onSaveInstanceState(outState,outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mPresenter?.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPresenter?.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter?.onActivityResult(requestCode, resultCode, data)
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mPresenter?.onNewIntent(intent)
    }

}
