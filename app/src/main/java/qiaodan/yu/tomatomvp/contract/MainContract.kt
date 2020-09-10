package qiaodan.yu.tomatomvp.contract

import com.fly.tomato.common.mvp.BaseView

interface MainContract {
    interface View : BaseView {
        fun showData(data: String)
    }
    interface Presenter {
        fun getDataById(id: Int)
    }
    interface Model {
        fun getDataById(id: Int): String?
    }
}