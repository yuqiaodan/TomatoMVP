package com.fly.tomato.common.base

import android.os.Bundle

import com.fly.tomato.common.mvp.BaseModel
import com.fly.tomato.common.mvp.BasePresenter

/**
 * Description: <BaseMvpFragment><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseMvpFragment> */
abstract class BaseMvpFragment<M : BaseModel, V, P : BasePresenter<M, V>> : BaseFragment() {
    protected var mPresenter: P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
        mPresenter?.attach(this as V)
        mPresenter?.injectLifecycle(mActivity)
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
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

    abstract fun initPresenter(): P
}
