package com.zwb.mvvm_mall.base.view

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.common.utils.CommonUtils

abstract class BaseVMActivity<VM : BaseViewModel<*>> : BaseActivity() {

    protected lateinit var mViewModel: VM

    override fun setContentView(){
        setContentView(layoutId)
        mViewModel = getActivityViewModelProvider(this).get(CommonUtils.getClass(this))
    }

    override fun initView() {
        mViewModel.loadState.observe(this,observer)
        initDataObserver()
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when {
                    it.code == StateType.SUCCESS -> showSuccess()
                    it.code == StateType.LOADING -> showLoading()
                    it.code == StateType.ERROR -> showError("网络出现问题啦")
                    it.code == StateType.NETWORK_ERROR -> showError("网络出现问题啦")
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
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}