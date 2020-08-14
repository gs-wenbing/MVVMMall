package com.zwb.mvvm_mall.ui.goods.view

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMActivity
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel

class GoodsDetailActivity : BaseVMActivity<GoodsViewModel>(){
    override val layoutId = R.layout.activity_goods_detail
    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }

    override fun initDataObserver() {
        super.initDataObserver()
    }

    companion object{
        fun launch(activity: FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, GoodsDetailActivity::class.java))
            }
    }
}