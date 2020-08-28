package qiaodan.yu.tomatomvp.presenter

import android.content.Context
import com.fly.tour.common.mvp.BasePresenter
import com.fly.tour.common.mvp.BaseView
import com.fly.tour.db.entity.NewsDetail
import qiaodan.yu.tomatomvp.contract.MainContract
import qiaodan.yu.tomatomvp.model.MainModel

class MainPresenter(mContext: Context) :BasePresenter<MainModel,MainContract.View>(mContext) ,MainContract.Presenter{

    override fun initModel(): MainModel {
        return MainModel(mContext)

    }

    override fun getDataById(id: Int) {
       val str= mModel?.getDataById(id)
        mView?.showData(str!!)
    }

}