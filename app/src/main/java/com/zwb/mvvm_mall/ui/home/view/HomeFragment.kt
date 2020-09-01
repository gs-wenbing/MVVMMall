package com.zwb.mvvm_mall.ui.home.view

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.ui.goods.view.GoodsDetailActivity
import com.zwb.mvvm_mall.ui.goods.view.SearchGoodsActivity
import com.zwb.mvvm_mall.ui.home.adapter.BannerImageAdapter
import com.zwb.mvvm_mall.ui.home.adapter.HomeGoodsAdapter
import com.zwb.mvvm_mall.ui.home.adapter.HomeListAdapter
import com.zwb.mvvm_mall.ui.home.adapter.MenuPagerAdapter
import com.zwb.mvvm_mall.ui.home.helper.SyncScrollHelper
import com.zwb.mvvm_mall.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_header.view.*
import kotlinx.android.synthetic.main.layout_home_item.view.*


class HomeFragment : BaseVMFragment<HomeViewModel>(){

    private lateinit var mHeaderView: View
    private lateinit var listAdapter: HomeListAdapter
    //横向RecyclerView的adapter
    lateinit var mHAdapter: HomeGoodsAdapter

    override var layoutId: Int = R.layout.fragment_home
    override fun initView() {
        super.initView()
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, mainToolbar)
        mainSearchLayout.setOnClickListener { SearchGoodsActivity.launch(mActivity,"") }

        listAdapter = HomeListAdapter(mActivity,arrayOf(1).asList().toMutableList())
        mainRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mainRecyclerView.adapter = listAdapter

        mHeaderView = LayoutInflater.from(context).inflate(R.layout.layout_home_header, null)
        listAdapter.addHeaderView(mHeaderView)

        mHeaderView.home_menu_viewpager2.offscreenPageLimit = 2
        mHeaderView.home_menu_viewpager2.adapter = MenuPagerAdapter(mActivity)
        mHeaderView.home_menu_indicator.setViewPager2(mHeaderView.home_menu_viewpager2, 2)

        mHAdapter = HomeGoodsAdapter(null,R.layout.item_goods_small_layout)
        mHAdapter.setPriceSize(14)
        mHeaderView.horizontalRecyclerview.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL,false)
        mHeaderView.horizontalRecyclerview.adapter = mHAdapter

        mHAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsDetailActivity.launch(requireActivity(),
                (adapter.getItem(position) as GoodsEntity).goodsName)
        }

        val syncScrollHelper = SyncScrollHelper(this)
        syncScrollHelper.initLayout()
        syncScrollHelper.syncRecyclerViewScroll(mainRecyclerView)
        syncScrollHelper.syncRefreshPullDown(mainRefreshLayout)

        mainRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            StatusBarUtil.darkMode(mActivity,false)
        }
    }
    override fun initData() {
        mViewModel.loadBannerCo()
        mViewModel.loadSeckillGoodsData()
    }

    override fun initDataObserver() {
        mViewModel.mBannerData.observe(this, Observer {
            setBannerData(it)
        })
        mViewModel.mSeckillGoods.observe(this, Observer {
            mHAdapter.setNewData(it)
            mainRefreshLayout.finishRefresh()
        })
    }
    private fun setBannerData(list: List<BannerResponse>) {
        mHeaderView.mBanner.adapter = BannerImageAdapter(list)
        mHeaderView.mBanner.addBannerLifecycleObserver(this)
        mHeaderView.mBanner.indicator = CircleIndicator(activity)
        mHeaderView.mBanner.setBannerRound2(20f)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


}

