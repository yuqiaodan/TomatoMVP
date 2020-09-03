package com.fly.tour.common.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle

import com.trello.rxlifecycle2.LifecycleProvider


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

    fun attachView(view: V) {
        mView = view
    }

    fun detachView() {
        mView = null
    }

    fun attachModel() {
        mModel = initModel()
    }

    fun detachModel() {
        mModel?.destory()
        mModel = null
    }

    abstract fun initModel(): M

    fun injectLifecycle(lifecycle: LifecycleProvider<*>) {
        mModel?.injectLifecycle(lifecycle)
    }

    fun onCreate(savedInstanceState: Bundle?) {}
    fun onDestroy() {
        detach()
    }

     fun onStart() {
    }
     fun onResume() {
    }

     fun onPause() {
    }

     fun onStop() {
    }

     fun onRestart() {
    }

     fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    }

     fun onRestoreInstanceState(savedInstanceState: Bundle) {
    }

     fun onRequestPermissionsResult(
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
