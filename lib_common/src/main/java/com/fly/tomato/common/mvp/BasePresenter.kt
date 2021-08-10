package com.fly.tomato.common.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle


/**
 * Description: <BasePresenter><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BasePresenter> */
abstract class BasePresenter<M : BaseModel, V>(protected var mContext: Context) {
    protected var mView: V? = null
    protected var mModel: M? = null

    fun attach(view: V) {
        attachView(view)
        attachModel()
    }

    fun detach() {
        detachView()
        detachModel()
    }

    private fun attachView(view: V) {
        mView = view
    }

    private fun detachView() {
        mView = null
    }

    private fun attachModel() {
        mModel = initModel()
    }

    private fun detachModel() {
        mModel?.destory()
        mModel = null
    }

    abstract fun initModel(): M

    open fun onCreate(savedInstanceState: Bundle?) {}
    open fun onDestroy() {
        detach()
    }

    open fun onStart() {
    }

    open fun onResume() {
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    open fun onRestart() {
    }

    open fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    }

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {
    }

    open fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    fun onNewIntent(intent: Intent?) {

    }

}
