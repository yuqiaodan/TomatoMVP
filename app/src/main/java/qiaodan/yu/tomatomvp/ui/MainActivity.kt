package qiaodan.yu.tomatomvp.ui

import com.fly.tour.common.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import qiaodan.yu.tomatomvp.R
import qiaodan.yu.tomatomvp.contract.MainContract
import qiaodan.yu.tomatomvp.model.MainModel
import qiaodan.yu.tomatomvp.presenter.MainPresenter

class MainActivity :BaseMvpActivity<MainModel,MainContract.View,MainPresenter>(),MainContract.View {
    override fun initPresenter(): MainPresenter {
       return MainPresenter(this)
    }

    override fun onBindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        tv_data.setOnClickListener {
            mPresenter?.getDataById(11223)
            mPresenter?.getMoreData()
        }
    }
    override fun showData(data: String) {
        tv_data.text = data
    }

    override fun enableToolbar(): Boolean {
        return false
    }


}