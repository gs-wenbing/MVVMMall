package com.zwb.module_my.fragment

import android.content.res.ColorStateList
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.*
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.lib_common.service.me.wrap.MeServiceWrap
import com.zwb.lib_common.service.order.wrap.OrderServiceWrap
import com.zwb.lib_common.view.PersistentStaggeredGridLayoutManager
import com.zwb.module_my.MeViewModel
import com.zwb.module_my.R
import com.zwb.module_my.activity.SettingActivity
import com.zwb.module_my.adapter.HomeGoodsAdapter
import com.zwb.module_my.databinding.FragmentMeBinding
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.layout_mine_header_frame.*
import kotlinx.android.synthetic.main.layout_mine_order_frame.*
import kotlinx.android.synthetic.main.layout_mine_vip_frame.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.min

class MeFragment : BaseFragment<FragmentMeBinding, MeViewModel>() {


    private var mOffset = 0
    private var mScrollY = 0
    private var lastScrollY = 0

    lateinit var mAdapter: HomeGoodsAdapter

    override val mViewModel by viewModels<MeViewModel>()

    override fun FragmentMeBinding.initView() {
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        StatusBarUtil.darkMode(requireActivity(),false)

//        toolbarAvatar.post {
//            BannerUtils.setBannerRound(toolbarAvatar, toolbarAvatar.height.toFloat())
//            BannerUtils.setBannerRound(ivAvatar, ivAvatar.height.toFloat())
//            BannerUtils.setBannerRound(llCard, 30f)
//        }

        barLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
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
                    barLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    LogUtils.e("scrollY==",scrollY.toString())
                    ivHeader.translationY = (mOffset - scrollY).toFloat()
                }
                lastScrollY = scrollY
                if (scrollY == 0) {
                    ivSetting.setImageResource(R.mipmap.setting_white)
                    ivFriend.setImageResource(R.mipmap.iv_friend_white)
                    StatusBarUtil.darkMode(requireActivity(),false)
                } else {
                    ivSetting.setImageResource(R.mipmap.setting_black)
                    ivFriend.setImageResource(R.mipmap.iv_friend_black)
                    StatusBarUtil.darkMode(requireActivity(),true)
                }
            }
        })
        setViewStyle()
        mAdapter = HomeGoodsAdapter(null)
        mineRecyclerView.layoutManager = PersistentStaggeredGridLayoutManager(2)
        mineRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsServiceWrap.instance.startGoodsDetail(requireActivity(),(adapter.getItem(position) as GoodsEntity).goodsName)
        }

        tvAllOrder.setOnClickListener {
            OrderServiceWrap.instance.startOrderList(requireActivity(), Constants.Order.ORDER_ALL)
        }

        ivSetting.setOnClickListener {
            SettingActivity.launch(requireActivity())
        }
    }
    private fun setViewStyle() {

        cloud1.setImageDrawable(
            UIUtils.tintDrawable(
                cloud1.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.F5766F))
            )
        )
        curve1.setImageDrawable(
            UIUtils.tintDrawable(
                curve1.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.F5766F))
            )
        )

        cloud2.setImageDrawable(
            UIUtils.tintDrawable(
                cloud2.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.E0C675))
            )
        )
        curve2.setImageDrawable(
            UIUtils.tintDrawable(
                curve2.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(requireActivity(),R.color.E0C675))
            )
        )

        UIUtils.setTextViewStyles(
            tvMineRecommend,
            intArrayOf(
                ContextCompat.getColor(requireActivity(),R.color.F5766F),
                ContextCompat.getColor(requireActivity(),R.color.colorPrimary),
                ContextCompat.getColor(requireActivity(),R.color.F5766F)
            ), floatArrayOf(0f, 0.5f, 1.0f)
        )
        UIUtils.setTextViewStyles(
            tvVipMsg, intArrayOf(
                ContextCompat.getColor(requireActivity(),R.color.white),
                ContextCompat.getColor(requireActivity(),R.color.E0C675),
                ContextCompat.getColor(requireActivity(),R.color.FEFBBE)
            ), floatArrayOf(0f, 0.7f, 1.0f)
        )
        UIUtils.setTextViewStyles(
            tvVipTitle,
            intArrayOf(ContextCompat.getColor(requireActivity(),R.color.E0C675), ContextCompat.getColor(requireActivity(),R.color.FEFBBE)),
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