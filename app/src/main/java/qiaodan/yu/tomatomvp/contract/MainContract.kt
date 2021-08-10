package qiaodan.yu.tomatomvp.contract

import qiaodan.yu.tomatomvp.https.GithubEntity


interface MainContract {
    interface View {
        fun showData(data: String)
        fun onSearchResult(result:GithubEntity?)
    }
    interface Presenter {
        fun getDataById(id: Int)
    }
    interface Model {
        fun getDataById(id: Int): String?
    }
}