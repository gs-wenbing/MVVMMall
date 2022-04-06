package com.zwb.module_oder.fragment

import androidx.fragment.app.viewModels
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.databinding.FragmentOrderBinding

class OrderFragment:BaseFragment<FragmentOrderBinding,OrderViewModel>() {
    override val mViewModel by viewModels<OrderViewModel>()

    override fun FragmentOrderBinding.initView() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}