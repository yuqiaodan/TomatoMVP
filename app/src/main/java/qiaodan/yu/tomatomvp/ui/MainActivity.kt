package qiaodan.yu.tomatomvp.ui

import android.os.Bundle
import com.fly.tomato.common.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import qiaodan.yu.tomatomvp.R
import qiaodan.yu.tomatomvp.contract.MainContract
import qiaodan.yu.tomatomvp.https.GithubEntity
import qiaodan.yu.tomatomvp.model.MainModel
import qiaodan.yu.tomatomvp.presenter.MainPresenter

class MainActivity : BaseMvpActivity<MainModel, MainContract.View, MainPresenter>(),
    MainContract.View {
    override fun initPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun onBindLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        tv_data.setOnClickListener {
            mPresenter?.getDataById(11223)
            mPresenter?.searchFromGithub()
        }

    }

    override fun showData(data: String) {
        tv_data.text = data
    }

    override fun onSearchResult(result: GithubEntity?) {
      if(result==null){
          //搜索失败
      }
    }


}