package qiaodan.yu.tomatomvp.model

import android.content.Context
import com.fly.tour.common.mvp.BaseModel
import qiaodan.yu.tomatomvp.contract.MainContract

class MainModel(context: Context) :BaseModel(context),MainContract.Model {
    override fun getDataById(id: Int): String? {
        return "data-num$id"
    }
}