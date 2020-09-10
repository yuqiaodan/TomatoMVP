package qiaodan.yu.tomatomvp.presenter

import android.content.Context
import android.util.Log
import com.fly.tomato.common.http.RxHttpUtils
import com.fly.tomato.common.http.interceptor.Transformer
import com.fly.tomato.common.http.observer.CommonObserver


import com.fly.tomato.common.mvp.BasePresenter

import qiaodan.yu.tomatomvp.contract.MainContract
import qiaodan.yu.tomatomvp.https.Api
import qiaodan.yu.tomatomvp.https.GithubEntity
import qiaodan.yu.tomatomvp.model.MainModel

class MainPresenter(mContext: Context) :BasePresenter<MainModel,MainContract.View>(mContext) ,MainContract.Presenter{

    override fun initModel(): MainModel {
        return MainModel(mContext)

    }

    override fun getDataById(id: Int) {
       val str= mModel?.getDataById(id)
        mView?.showData(str!!)
    }

    fun searchFromGithub(){
        RxHttpUtils
            .createApi(Api::class.java)
            .searchFromGithub("event bus")
            .compose(Transformer.switchSchedulers())
            .subscribe(object: CommonObserver<GithubEntity>(){
                override fun onError(errorMsg: String?) {
                   Log.d("http test","error$errorMsg")
                }

                override fun onSuccess(t: GithubEntity?) {
                    Log.d("http test","success${t?.total_count}")
                }
            })
    }
}