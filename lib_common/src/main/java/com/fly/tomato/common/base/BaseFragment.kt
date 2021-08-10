package com.fly.tomato.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fly.tomato.common.R
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus

/**
 * Description: <BaseFragment><br>
 * Author:      mxdl<br>
 * Date:        2018/1/15<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</BaseFragment> */
abstract class BaseFragment : Fragment() {
    protected lateinit var mActivity: RxAppCompatActivity
    protected lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity as RxAppCompatActivity?)!!
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(onBindLayout(), container, false)
        return mView
    }



    abstract fun onBindLayout(): Int



}
