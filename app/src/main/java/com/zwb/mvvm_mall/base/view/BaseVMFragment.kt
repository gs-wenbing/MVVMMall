package com.zwb.mvvm_mall.base.view

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.zwb.mvvm_mall.MyApplication
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.common.utils.CommonUtils

abstract class BaseVMFragment<VM:BaseViewModel<*>>:BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun initView() {
        super.initView()
        mViewModel.loadState.observe(this,observer)
        initDataObserver()
    }

    override fun setContentView(inflater: LayoutInflater,container: ViewGroup?): View {
        val rootView = inflater.inflate(layoutId,container,false)
        mViewModel = getActivityViewModelProvider(mActivity).get(CommonUtils.getClass(this))
        return rootView
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when {
                    it.code == StateType.SUCCESS -> showSuccess()
                    it.code == StateType.LOADING -> showLoading()
                    it.code == StateType.ERROR -> showError("网络异常")
                    it.code == StateType.NETWORK_ERROR -> showError("网络异常")
                    it.code == StateType.EMPTY -> showEmpty()
                }
            }
        }
    }
    open fun initDataObserver() {}

    open fun showLoading() {}

    open fun showSuccess() {}

    open fun showEmpty() {}

    open fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(MyApplication.INSTANCE, msg, Toast.LENGTH_SHORT).show()
        }
    }

}