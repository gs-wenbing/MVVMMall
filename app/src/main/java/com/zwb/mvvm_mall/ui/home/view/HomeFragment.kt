package com.zwb.mvvm_mall.ui.home.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.ui.home.adapter.BannerImageAdapter
import com.zwb.mvvm_mall.ui.home.adapter.HomeGoodsAdapter
import com.zwb.mvvm_mall.ui.home.viewmodel.HomeViewModel
import com.zwb.mvvm_mall.ui.home.adapter.MultiTypeAdapter
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.ui.search.view.SearchActivity
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.common.view.nested.bean.CategoryBean
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.homeRecyclerView
import kotlinx.android.synthetic.main.layout_home_header.view.*
import kotlinx.android.synthetic.main.layout_home_toolbar.*
import java.util.*
import kotlin.collections.HashMap


@SuppressLint("Registered")
class HomeFragment : BaseVMFragment<HomeViewModel>(),ChildRecyclerView.OnRecyclerViewLoadListener {

    lateinit var mHeaderView: View
    private val mTitles = arrayOf("精选", "新品", "直播", "实惠", "买家秀")
    private val mDataList = ArrayList<CategoryBean>()
    private var mCacheVies:HashMap<String, CategoryView>?= null
    private val mCategoryBean = CategoryBean()
    private lateinit var multiTypeAdapter:MultiTypeAdapter

    //横向RecyclerView的adapter
    lateinit var mHAdapter: HomeGoodsAdapter

    override val layoutId: Int = R.layout.fragment_home
    override fun initView() {
        super.initView()
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        textSearch.setOnClickListener { SearchActivity.launch(requireActivity()) }
        mHeaderView = LayoutInflater.from(context).inflate(R.layout.layout_home_header, null)
        homeRecyclerView.initLayoutManager()

        mCategoryBean.tabTitleList.clear()
        mCategoryBean.tabTitleList.addAll(mTitles.asList())
        mDataList.add(mCategoryBean)

        multiTypeAdapter = MultiTypeAdapter(mDataList, this)
        homeRecyclerView.adapter = multiTypeAdapter
        multiTypeAdapter.addHeaderView(mHeaderView)

        multiTypeAdapter.notifyDataSetChanged()

        mHAdapter = HomeGoodsAdapter(R.layout.item_goods_small_layout,null)
        mHeaderView.horizontalRecyclerview.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL,false)
        mHeaderView.horizontalRecyclerview.adapter = mHAdapter

        mCacheVies = multiTypeAdapter.getRecyclerViewList()
//        swipeRefreshLayout.setColorSchemeColors(Color.RED)
        swipeRefreshLayout.setOnRefreshListener {
            refresh()
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

    override fun onRecyclerViewInitData(index:Int) {
        when (index) {
            0 -> mViewModel.loadRecyclerGoodsData0()
            1 -> mViewModel.loadRecyclerGoodsData1()
            2 -> mViewModel.loadRecyclerGoodsData2()
            3 -> mViewModel.loadRecyclerGoodsData3()
        }
    }


    override fun onRecyclerViewLoadMore(index:Int): Boolean {
        when (index) {
            0 -> mViewModel.loadRecyclerGoodsData0()
            1 -> mViewModel.loadRecyclerGoodsData1()
            2 -> mViewModel.loadRecyclerGoodsData2()
            3 -> mViewModel.loadRecyclerGoodsData3()
        }
        return true
    }

    private fun refresh() {
        swipeRefreshLayout.isRefreshing = false
    }
    override fun initDataObserver() {
        mViewModel.mBannerData.observe(this, Observer { response->
            response?.let {
                setBannerData(it)
            }
        })
        mViewModel.mSeckillGoods.observe(this, Observer {
            it?.let {
                mHAdapter.setNewData(it)
            }
        })
        mViewModel.mRecyclerGoods0.observe(this, Observer {
            it?.let {
                setRecyclerViewData(0,it)
            }
        })
        mViewModel.mRecyclerGoods1.observe(this, Observer {
            it?.let {
                setRecyclerViewData(1,it)
            }
        })
        mViewModel.mRecyclerGoods2.observe(this, Observer {
            it?.let {
                setRecyclerViewData(2,it)
            }
        })
        mViewModel.mRecyclerGoods3.observe(this, Observer {
            it?.let {
                setRecyclerViewData(3,it)
            }
        })
    }

    private fun setBannerData(list: List<BannerResponse>) {
        val adapter = BannerImageAdapter(list)
        mHeaderView.mBanner?.let {
            it.addBannerLifecycleObserver(this)
            it.indicator = CircleIndicator(activity)
            it.setBannerRound2(20f)
            it.adapter = adapter
        }
    }
    private fun setRecyclerViewData(index:Int,list:List<GoodsEntity>){
        if(mCacheVies==null){
            mCacheVies = multiTypeAdapter.getRecyclerViewList()
        }
        mCacheVies!![mCategoryBean.tabTitleList[index]]!!.setData(isRefrash = false,list = list)
    }
    override fun onDestroy() {
        super.onDestroy()
        multiTypeAdapter.destroy()
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


}

