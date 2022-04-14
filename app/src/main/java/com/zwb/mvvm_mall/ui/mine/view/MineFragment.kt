package com.zwb.mvvm_mall.ui.mine.view

import android.content.res.ColorStateList
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.LogUtils
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.common.utils.UIUtils
import com.zwb.mvvm_mall.common.view.PersistentStaggeredGridLayoutManager
import com.zwb.mvvm_mall.ui.goods.view.GoodsDetailActivity
import com.zwb.mvvm_mall.ui.home.adapter.HomeGoodsAdapter
import com.zwb.mvvm_mall.ui.mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_mine_header_frame.*
import kotlinx.android.synthetic.main.layout_mine_vip_frame.*
import kotlin.math.min


class MineFragment : BaseVMFragment<MineViewModel>(){
    private var mOffset = 0
    private var mScrollY = 0
    private var lastScrollY = 0

    lateinit var mAdapter: HomeGoodsAdapter

    override var layoutId = R.layout.fragment_mine
    override fun initView() {
        super.initView()
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        StatusBarUtil.darkMode(mActivity,false)

        toolbarAvatar.post {
            BannerUtils.setBannerRound(toolbarAvatar, toolbarAvatar.height.toFloat())
            BannerUtils.setBannerRound(ivAvatar, ivAvatar.height.toFloat())
            BannerUtils.setBannerRound(llCard, 30f)
        }

        barLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            var h = UIUtils.dp2px(170f)
            var color = ContextCompat.getColor(mActivity, R.color.white) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (lastScrollY < h) {
                    mScrollY = min(h, scrollY)
                    barLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    LogUtils.e("scrollY==",scrollY.toString())
                    ivHeader.translationY = (mOffset - scrollY).toFloat()
                }
                lastScrollY = scrollY
                if (scrollY == 0) {
                    ivMenu.setImageResource(R.mipmap.setting_white)
                    ivFriend.setImageResource(R.mipmap.iv_friend_white)
                    StatusBarUtil.darkMode(mActivity,false)
                } else {
                    ivMenu.setImageResource(R.mipmap.setting_black)
                    ivFriend.setImageResource(R.mipmap.iv_friend_black)
                    StatusBarUtil.darkMode(mActivity,true)
                }
            }
        })
        setViewStyle()
        mAdapter = HomeGoodsAdapter(null)
        mineRecyclerView.layoutManager = PersistentStaggeredGridLayoutManager(2)
        mineRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsDetailActivity.launch(requireActivity(),
                (adapter.getItem(position) as GoodsEntity).goodsName)
        }
        registerDefaultLoad(mineRecyclerView,Constant.URL_SECKILL_GOODS)
    }

    private fun setViewStyle() {

        cloud1.setImageDrawable(
            UIUtils.tintDrawable(
                cloud1.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.F5766F))
            )
        )
        curve1.setImageDrawable(
            UIUtils.tintDrawable(
                curve1.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.F5766F))
            )
        )

        cloud2.setImageDrawable(
            UIUtils.tintDrawable(
                cloud2.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.E0C675))
            )
        )
        curve2.setImageDrawable(
            UIUtils.tintDrawable(
                curve2.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.E0C675))
            )
        )

        UIUtils.setTextViewStyles(
            tvMineRecommend,
            intArrayOf(
                ContextCompat.getColor(mContext,R.color.F5766F),
                ContextCompat.getColor(mContext,R.color.mainRed),
                ContextCompat.getColor(mContext,R.color.F5766F)
            ), floatArrayOf(0f, 0.5f, 1.0f)
        )
        UIUtils.setTextViewStyles(
            tvVipMsg, intArrayOf(
                ContextCompat.getColor(mContext,R.color.white),
                ContextCompat.getColor(mContext,R.color.E0C675),
                ContextCompat.getColor(mContext,R.color.FEFBBE)
            ), floatArrayOf(0f, 0.7f, 1.0f)
        )
        UIUtils.setTextViewStyles(
            tvVipTitle,
            intArrayOf(ContextCompat.getColor(mContext,R.color.E0C675), ContextCompat.getColor(mContext,R.color.FEFBBE)),
            floatArrayOf(0f, 0.6f)
        )

        tvVipMsg.text = UIUtils.setTextViewContentStyle(
            tvVipMsg.text.toString(),
            AbsoluteSizeSpan(UIUtils.dp2px(16f)),
            null,
            4, 5
        )
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && lastScrollY==0) {
            StatusBarUtil.darkMode(mActivity,false)
        }else{
            StatusBarUtil.darkMode(mActivity,true)
        }
    }
    override fun initData() {
        super.initData()
        mViewModel.loadSeckillGoodsData(Constant.URL_SECKILL_GOODS)
    }

    override fun initDataObserver() {
        mViewModel.mSeckillGoods.observe(this, Observer {
            mAdapter.setNewData(it.toMutableList())
        })
    }

    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}