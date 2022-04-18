package com.zwb.module_home.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.indicator.CircleIndicator
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.base.BaseVMFragment
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.module_home.HomeApi
import com.zwb.module_home.HomeViewModel
import com.zwb.module_home.R
import com.zwb.module_home.adapter.BannerImageAdapter
import com.zwb.module_home.adapter.HomeListAdapter
import com.zwb.module_home.adapter.MenuGridAdapter
import com.zwb.module_home.adapter.SeckillGoodsAdapter
import com.zwb.module_home.bean.BannerEntity
import com.zwb.module_home.bean.SeckillGoodsEntity
import com.zwb.module_home.bean.SeckillMoreEntity
import com.zwb.module_home.databinding.HomeFragmentHomeBinding
import com.zwb.module_home.view.SyncScrollHelper
import kotlinx.android.synthetic.main.home_fragment_menu_grid.*
import kotlinx.android.synthetic.main.home_layout_home_header.view.*
import kotlinx.android.synthetic.main.home_layout_home_item.view.*

class HomeFragment : BaseVMFragment<HomeFragmentHomeBinding, HomeViewModel>() {

    private lateinit var mHeaderView: View
    private lateinit var listAdapter: HomeListAdapter

    //横向RecyclerView的adapter
    lateinit var mHAdapter: SeckillGoodsAdapter


    override val mViewModel by viewModels<HomeViewModel>()

    override fun HomeFragmentHomeBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.setPaddingSmart(requireContext(), mainToolbar)
        mBinding.mainSearchLayout.setOnClickListener {
            GoodsServiceWrap.instance.startGoodsList(requireActivity(), "")
        }
        listAdapter = HomeListAdapter(this@HomeFragment, arrayOf(1).asList().toMutableList())
        mBinding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        mBinding.mainRecyclerView.adapter = listAdapter

        mHeaderView = LayoutInflater.from(context).inflate(R.layout.home_layout_home_header, null)
        listAdapter.addHeaderView(mHeaderView)
        mHeaderView.rvMenu.layoutManager =
            GridLayoutManager(requireActivity(), 2, LinearLayoutManager.HORIZONTAL, false)
        mHeaderView.rvMenu.adapter = MenuGridAdapter(requireActivity())
        mHeaderView.hIndicator.bindRecyclerView(mHeaderView.rvMenu)
//        mHeaderView.rvMenu.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                //整体的总宽度，注意是整体，包括在显示区域之外的
//                //滚动条表示的总范围
//                var range = 0;
//                val temp = mHeaderView.rvMenu.computeHorizontalScrollRange()
//                if (temp > range) {
//                    range = temp
//                }
//                //滑块的偏移量
//                val offset = mHeaderView.rvMenu.computeHorizontalScrollOffset()
//                //可视区域长度
//                val extent = mHeaderView.rvMenu.computeHorizontalScrollExtent()
//                //滑出部分在剩余范围的比例
//                val proportion =  (offset * 1.0 / (range - extent))
//                //计算滚动条宽度
//                val transMaxRange = mHeaderView.rlIndicator.width - mHeaderView.viewLine.width
//                //设置滚动条移动
//                mHeaderView.viewLine.translationX = (transMaxRange * proportion).toFloat()
//            }
//        })
        mHAdapter = SeckillGoodsAdapter(null)
        mHeaderView.horizontalRecyclerview.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        mHeaderView.horizontalRecyclerview.adapter = mHAdapter
        mHAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsServiceWrap.instance.startGoodsDetail(
                requireActivity(),
                (adapter.getItem(position) as GoodsEntity).goodsName
            )
        }

        val syncScrollHelper = SyncScrollHelper(this@HomeFragment)
        syncScrollHelper.initLayout()
        syncScrollHelper.syncRecyclerViewScroll(mainRecyclerView)
        syncScrollHelper.syncRefreshPullDown(mainRefreshLayout)

        mainRefreshLayout.setOnRefreshListener {
            initRequestData()
        }

        setDefaultLoad(mHeaderView.mBanner, HomeApi.BANNER_URL)

        setPlaceHolderLoad(
            mHeaderView.horizontalRecyclerview,
            R.layout.layout_placeholder_home_h_veiw,
            HomeApi.SECKILL_URL
        )
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
        mViewModel.loadBannerCo().observe(viewLifecycleOwner, {
            setBannerData(it)
        })
        mViewModel.loadSeckillGoodsData().observe(viewLifecycleOwner, {
            val l: List<MultiItemEntity> = it.flatMap { goods ->
                listOf(SeckillGoodsEntity(goods))
            }
            mHAdapter.setNewData(l.subList(0, 12))
//            mHAdapter.addData(SeckillMoreEntity("查看更多"))
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

