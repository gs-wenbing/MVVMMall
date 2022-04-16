package com.zwb.mvvm_mall.ui.home.view

import android.os.Handler
import androidx.lifecycle.Observer
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.common.view.PersistentStaggeredGridLayoutManager
import com.zwb.mvvm_mall.ui.home.adapter.PagerListAdapter
import com.zwb.mvvm_mall.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home_page_list.*

class HomePageSellerShowFragment : BaseVMFragment<HomeViewModel>(){

    var mAdapter = PagerListAdapter(R.layout.item_goods_big_layout,null)

    override var layoutId = R.layout.fragment_home_page_list

    override fun initView() {
        super.initView()
        childRecyclerView.layoutManager = PersistentStaggeredGridLayoutManager(2)
        childRecyclerView.adapter = mAdapter
        registerPlaceHolderLoad(childRecyclerView,R.layout.layout_placeholder_home_list)
    }

    override fun initData() {
        super.initData()
        Handler().postDelayed({
            mViewModel.loadRecyclerGoodsData4()
        }, 2000)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mRecyclerGoods4.observe(viewLifecycleOwner, Observer {
            showSuccess(Constant.COMMON_KEY)
            mAdapter.setNewData(it.toMutableList())
        })
    }

}