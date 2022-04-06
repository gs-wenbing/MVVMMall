package com.zwb.module_my

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_my.databinding.ActivityMyBinding
import com.zwb.module_my.fragment.MyFragment

class MyActivity : BaseActivity<ActivityMyBinding, MyViewModel>() {

    override val mViewModel by viewModels<MyViewModel>()

    override fun ActivityMyBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent,MyFragment()).commit()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}
