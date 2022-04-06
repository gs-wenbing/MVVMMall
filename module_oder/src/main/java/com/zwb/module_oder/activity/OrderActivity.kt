package com.zwb.module_oder.activity

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.R
import com.zwb.module_oder.databinding.ActivityOrderBinding
import com.zwb.module_oder.fragment.OrderFragment

class OrderActivity : BaseActivity<ActivityOrderBinding, OrderViewModel>() {
    override val mViewModel by viewModels<OrderViewModel>()

    override fun ActivityOrderBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent, OrderFragment()).commit()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

}