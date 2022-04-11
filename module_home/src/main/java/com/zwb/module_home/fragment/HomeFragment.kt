package com.zwb.module_home.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.youth.banner.indicator.CircleIndicator
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.module_home.HomeApi
import com.zwb.module_home.HomeViewModel
import com.zwb.module_home.R
import com.zwb.module_home.adapter.BannerImageAdapter
import com.zwb.module_home.adapter.HomeHorizontalGoodsAdapter
import com.zwb.module_home.adapter.HomeListAdapter
import com.zwb.module_home.adapter.MenuPagerAdapter
import com.zwb.module_home.bean.BannerEntity
import com.zwb.module_home.databinding.HomeFragmentHomeBinding
import com.zwb.module_home.view.SyncScrollHelper
import kotlinx.android.synthetic.main.home_layout_home_header.view.*
import kotlinx.android.synthetic.main.home_layout_home_item.view.*

class HomeFragment:BaseFragment<HomeFragmentHomeBinding,HomeViewModel>() {

    private lateinit var mHeaderView: View
    private lateinit var listAdapter: HomeListAdapter
    //横向RecyclerView的adapter
    lateinit var mHAdapter: HomeHorizontalGoodsAdapter


    override val mViewModel by viewModels<HomeViewModel>()

    override fun HomeFragmentHomeBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.setPaddingSmart(requireActivity(), mainToolbar)
        mBinding.mainSearchLayout.setOnClickListener {
            GoodsServiceWrap.instance.startGoodsList(requireActivity(),"")
        }
        listAdapter = HomeListAdapter(requireActivity(),arrayOf(1).asList().toMutableList())
        mBinding.mainRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.mainRecyclerView.adapter = listAdapter

        mHeaderView = LayoutInflater.from(context).inflate(R.layout.home_layout_home_header, null)
        listAdapter.addHeaderView(mHeaderView)

        mHeaderView.home_menu_viewpager2.offscreenPageLimit = 2
        mHeaderView.home_menu_viewpager2.adapter = MenuPagerAdapter(requireActivity())
        mHeaderView.home_menu_indicator.setViewPager2(mHeaderView.home_menu_viewpager2, 2)

        mHAdapter = HomeHorizontalGoodsAdapter(null,R.layout.item_goods_small_layout)
        mHAdapter.setPriceSize(14)
        mHeaderView.horizontalRecyclerview.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL,false)
        mHeaderView.horizontalRecyclerview.adapter = mHAdapter

        mHAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsServiceWrap.instance.startGoodsDetail(requireActivity(),(adapter.getItem(position) as GoodsEntity).goodsName)
        }

        val syncScrollHelper = SyncScrollHelper(this@HomeFragment)
        syncScrollHelper.initLayout()
        syncScrollHelper.syncRecyclerViewScroll(mainRecyclerView)
        syncScrollHelper.syncRefreshPullDown(mainRefreshLayout)

        mainRefreshLayout.setOnRefreshListener {
            initRequestData()
        }
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
//            StatusBarUtil.darkMode(requireActivity(),false)
        }
    }
    override fun initObserve() {

    }

    override fun initRequestData() {
        mViewModel.loadBannerCo(HomeApi.BASE_URL).observe(this, {
            setBannerData(it)
        })
        mViewModel.loadSeckillGoodsData().observe(this,{
            mHAdapter.setNewData(it)
            mBinding.mainRefreshLayout.finishRefresh()
        })
    }

    private fun setBannerData(list: List<BannerEntity>) {
        mHeaderView.mBanner.adapter = BannerImageAdapter(list)
        mHeaderView.mBanner.addBannerLifecycleObserver(this)
        mHeaderView.mBanner.indicator = CircleIndicator(activity)
        mHeaderView.mBanner.setBannerRound2(20f)
    }
}