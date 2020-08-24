package com.zwb.mvvm_mall.ui.home.view

import androidx.lifecycle.Observer
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.common.utils.dp2px
import com.zwb.mvvm_mall.common.view.GridItemDecoration
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
        childRecyclerView.addItemDecoration(GridItemDecoration(activity!!.dp2px(8f)))
        childRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        super.initData()
        mViewModel.loadRecyclerGoodsData4()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mRecyclerGoods4.observe(this, Observer {
            mAdapter.setNewData(it.toMutableList())
        })
    }

}