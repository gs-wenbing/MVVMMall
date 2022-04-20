package com.zwb.module_me.fragment

import android.content.res.ColorStateList
import android.text.style.AbsoluteSizeSpan
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.lib_common.service.order.wrap.OrderServiceWrap
import com.zwb.lib_common.view.PersistentStaggeredGridLayoutManager
import com.zwb.module_me.MeViewModel
import com.zwb.module_me.R
import com.zwb.module_me.activity.SettingActivity
import com.zwb.module_me.adapter.HomeGoodsAdapter
import com.zwb.module_me.databinding.FragmentMeBinding
import kotlin.math.min

class MeFragment : BaseFragment<FragmentMeBinding, MeViewModel>() {


    private var mOffset = 0
    private var mScrollY = 0
    private var lastScrollY = 0

    private lateinit var mAdapter: HomeGoodsAdapter

    override val mViewModel by viewModels<MeViewModel>()

    override fun FragmentMeBinding.initView() {
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, mBinding.toolbar)
        StatusBarUtil.darkMode(requireActivity(),false)

        this.toolbarAvatar.post {
            BannerUtils.setBannerRound(mBinding.toolbarAvatar, mBinding.toolbarAvatar.height.toFloat())
            BannerUtils.setBannerRound(mBinding.includeHeader.ivAvatar, mBinding.includeHeader.ivAvatar.height.toFloat())
        }

        mBinding.barLayout.alpha = 0f
        mBinding.toolbar.setBackgroundColor(0)
        mBinding.scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            var h = UIUtils.dp2px(170f)
            var color = ContextCompat.getColor(requireActivity(), R.color.white) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (lastScrollY < h) {
                    mScrollY = min(h, scrollY)
                    mBinding.barLayout.alpha = 1f * mScrollY / h
                    mBinding.toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    LogUtils.e("scrollY==",scrollY.toString())
                    mBinding.ivHeader.translationY = (mOffset - scrollY).toFloat()
                }
                lastScrollY = scrollY
                if (scrollY == 0) {
                    mBinding.ivSetting.setImageResource(R.mipmap.setting_white)
                    mBinding.ivFriend.setImageResource(R.mipmap.iv_friend_white)
                    StatusBarUtil.darkMode(requireActivity(),false)
                } else {
                    mBinding.ivSetting.setImageResource(R.mipmap.setting_black)
                    mBinding.ivFriend.setImageResource(R.mipmap.iv_friend_black)
                    StatusBarUtil.darkMode(requireActivity(),true)
                }
            }
        })
        setViewStyle()
        mAdapter = HomeGoodsAdapter(null)
        mBinding.mineRecyclerView.layoutManager = PersistentStaggeredGridLayoutManager(2)
        mBinding.mineRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsServiceWrap.instance.startGoodsDetail(requireActivity(),(adapter.getItem(position) as GoodsEntity).goodsName)
        }

        mBinding.includeOrder.tvAllOrder.setOnClickListener {
            OrderServiceWrap.instance.startOrderList(requireActivity(), Constants.Order.ORDER_ALL)
        }

        mBinding.ivSetting.setOnClickListener {
            SettingActivity.launch(requireActivity())
        }
    }
    private fun setViewStyle() {

        mBinding.cloud1.setImageDrawable(
            UIUtils.tintDrawable(
                mBinding.cloud1.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.F5766F))
            )
        )
        mBinding.curve1.setImageDrawable(
            UIUtils.tintDrawable(
                mBinding.curve1.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.F5766F))
            )
        )

        mBinding.cloud2.setImageDrawable(
            UIUtils.tintDrawable(
                mBinding.cloud2.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.E0C675))
            )
        )
        mBinding.curve2.setImageDrawable(
            UIUtils.tintDrawable(
                mBinding.curve2.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.E0C675))
            )
        )

        UIUtils.setTextViewStyles(
            mBinding.tvMineRecommend,
            intArrayOf(
                ContextCompat.getColor(requireActivity(),R.color.F5766F),
                ContextCompat.getColor(requireActivity(),R.color.colorPrimary),
                ContextCompat.getColor(requireActivity(),R.color.F5766F)
            ), floatArrayOf(0f, 0.5f, 1.0f)
        )
        UIUtils.setTextViewStyles(
            mBinding.includeVip.tvVipMsg, intArrayOf(
                ContextCompat.getColor(requireActivity(),R.color.white),
                ContextCompat.getColor(requireActivity(),R.color.E0C675),
                ContextCompat.getColor(requireActivity(),R.color.FEFBBE)
            ), floatArrayOf(0f, 0.7f, 1.0f)
        )
        UIUtils.setTextViewStyles(
            mBinding.includeVip.tvVipTitle,
            intArrayOf(ContextCompat.getColor(requireActivity(),R.color.E0C675), ContextCompat.getColor(requireActivity(),R.color.FEFBBE)),
            floatArrayOf(0f, 0.6f)
        )

        mBinding.includeVip.tvVipMsg.text = UIUtils.setTextViewContentStyle(
            mBinding.includeVip.tvVipMsg.text.toString(),
            AbsoluteSizeSpan(UIUtils.dp2px(16f)),
            null,
            4, 5
        )
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && lastScrollY==0) {
            StatusBarUtil.darkMode(requireActivity(),false)
        }else{
            StatusBarUtil.darkMode(requireActivity(),true)
        }
    }
    override fun initObserve() {
        mViewModel.mSeckillGoods.observe(viewLifecycleOwner, {
            mAdapter.setNewData(it.toMutableList())
        })
    }

    override fun initRequestData() {
        mViewModel.loadSeckillGoodsData()
    }
}