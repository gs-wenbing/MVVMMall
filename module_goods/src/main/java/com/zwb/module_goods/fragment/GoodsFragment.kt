package com.zwb.module_goods.fragment

import androidx.fragment.app.viewModels
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.databinding.FragmentGoodsBinding

class GoodsFragment:BaseFragment<FragmentGoodsBinding,GoodsViewModel>() {
    override val mViewModel by viewModels<GoodsViewModel>()

    override fun FragmentGoodsBinding.initView() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}