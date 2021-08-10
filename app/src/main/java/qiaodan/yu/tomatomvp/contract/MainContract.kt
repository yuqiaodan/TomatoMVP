package qiaodan.yu.tomatomvp.contract


interface MainContract {
    interface View {
        fun showData(data: String)
    }
    interface Presenter {
        fun getDataById(id: Int)
    }
    interface Model {
        fun getDataById(id: Int): String?
    }
}