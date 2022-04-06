package com.zwb.module_goods.activity

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.R
import com.zwb.module_goods.databinding.ActivityGoodsBinding
import com.zwb.module_goods.fragment.GoodsFragment

class GoodsActivity : BaseActivity<ActivityGoodsBinding, GoodsViewModel>() {
    override val mViewModel by viewModels<GoodsViewModel>()

    override fun ActivityGoodsBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent, GoodsFragment()).commit()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

}
