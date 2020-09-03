package com.zwb.mvvm_mall.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zwb.mvvm_mall.MyApplication
import com.zwb.mvvm_mall.base.vm.SharedViewModel

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mSharedViewModel: SharedViewModel
    private var mActivityProvider: ViewModelProvider? = null
    abstract val layoutId: Int

    open fun initView() {}

    open fun initData() {}

    open fun reLoad() = initData()

    abstract fun setContentView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        mSharedViewModel = getAppViewModelProvider().get(SharedViewModel::class.java)
        initView()
        initData()
    }
    private fun getAppViewModelProvider(): ViewModelProvider {
        return (applicationContext as MyApplication).getAppViewModelProvider(this)
    }

    protected fun getActivityViewModelProvider(activity: AppCompatActivity): ViewModelProvider {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(activity)
        }
        return mActivityProvider as ViewModelProvider
    }
}
