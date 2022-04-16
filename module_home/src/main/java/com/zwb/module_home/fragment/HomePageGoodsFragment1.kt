package com.zwb.module_home.fragment

import android.os.Handler
import androidx.fragment.app.viewModels
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_common.base.BaseVMFragment
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.lib_common.view.PersistentStaggeredGridLayoutManager
import com.zwb.module_home.HomeApi
import com.zwb.module_home.HomeViewModel
import com.zwb.module_home.R
import com.zwb.module_home.adapter.PagerListAdapter
import com.zwb.module_home.databinding.HomeFragmentPageListBinding


class HomePageGoodsFragment1 : BaseVMFragment<HomeFragmentPageListBinding, HomeViewModel>(){

    private var mAdapter = PagerListAdapter(R.layout.item_goods_big_layout, null)

    override val mViewModel by viewModels<HomeViewModel>()

    override fun HomeFragmentPageListBinding.initView() {
        childRecyclerView.layoutManager =  PersistentStaggeredGridLayoutManager(2)
        childRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsServiceWrap.instance.startGoodsDetail(requireActivity(),(adapter.getItem(position) as GoodsEntity).goodsName)
        }
        setPlaceHolderLoad(childRecyclerView,R.layout.layout_placeholder_home_list, HomeApi.SECKILL_URL)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.loadSeckillGoodsData().observe(viewLifecycleOwner, {
            mAdapter.setNewData(it.toMutableList())
        })
    }

}