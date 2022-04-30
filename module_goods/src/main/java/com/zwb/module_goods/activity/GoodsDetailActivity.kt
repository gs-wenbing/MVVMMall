package com.zwb.module_goods.activity

import android.content.Intent
import android.graphics.Typeface
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer
import com.youth.banner.listener.OnPageChangeListener
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.view.PersistentStaggeredGridLayoutManager
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.R
import com.zwb.module_goods.adapter.GoodsBannerAdapter
import com.zwb.module_goods.adapter.HomeGoodsAdapter
import com.zwb.module_goods.bean.CommentEntity
import com.zwb.module_goods.bean.GoodsBannerEntity
import com.zwb.module_goods.databinding.ActivityGoodsDetailBinding
import com.zwb.module_goods.fragment.GoodsCommentFragment
import com.zwb.module_goods.view.NumIndicator
import kotlin.math.min

@Route(path = RoutePath.Goods.PAGE_GOODS_DETAIL)
class GoodsDetailActivity:BaseActivity<ActivityGoodsDetailBinding, GoodsViewModel>() {
    override val mViewModel by viewModels<GoodsViewModel>()

    @Autowired(name = Constants.Goods.PARAMS_GOODS_NAME)
    lateinit var goodsName: String

    private var mScrollY = 0
    private var lastScrollY = 0

    var bannerPlayer: NormalGSYVideoPlayer? = null

    private var mCommentFragment: GoodsCommentFragment? = null

    override fun ActivityGoodsDetailBinding.initView() {
        ARouter.getInstance().inject(this@GoodsDetailActivity)
        StatusBarUtil.immersive(this@GoodsDetailActivity)
        StatusBarUtil.setPaddingSmart(this@GoodsDetailActivity, mBinding.includeToolbar.toolbar)
        mBinding.includeToolbar.ivBack.setOnClickListener {
            if(mCommentFragment!=null && mCommentFragment!!.isAdded){
                closeCommentFragment()
            }else{
                finish()
            }
        }
        mBinding.includeGoods.tvGoodsName.text = goodsName
        mBinding.includeToolbar.radioTabs.alpha = 0f
        mBinding.includeToolbar.toolbar.setBackgroundColor(0)

        initBannerData()
        initListener()

    }

    override fun initObserve() {
        mViewModel.mSeckillGoods.observe(this, Observer {
            mBinding.rvDetail.layoutManager = PersistentStaggeredGridLayoutManager(2)
            mBinding.rvDetail.adapter = HomeGoodsAdapter(it.toMutableList())

            mBinding.includeComment.rvRecommend.layoutManager = GridLayoutManager(this, 3)
            mBinding.includeComment.rvRecommend.adapter = HomeGoodsAdapter(
                it.toMutableList().subList(0, 6),
                R.layout.item_round_goods_layout
            )
        })
        mViewModel.mCommentList.observe(this, Observer {
            setCommentData(it)
        })
    }

    override fun initRequestData() {
        mViewModel.loadSeckillGoodsData("")
        mViewModel.loadCommentList()
    }

    private fun initListener(){
        mBinding.svDetail.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            var h = UIUtils.dp2px(170f)
            var color =
                ContextCompat.getColor(this@GoodsDetailActivity, R.color.white) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (lastScrollY < h) {
                    mScrollY = min(h, scrollY)
                    mBinding.includeToolbar.radioTabs.alpha = 1f * mScrollY / h
                    mBinding.includeToolbar.toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                }
//                if (lastScrollY>mBinding.includeGoods.banner.height+toolbar.height) {
//                    if(bannerPlayer?.isInPlayingState!!){
//                        mainPlayer.visibility = View.VISIBLE
//                        mainPlayer.setUp("https://vod.300hu.com/4c1f7a6atransbjngwcloud1oss/444b0379221736599300878337/v.f30.mp4?dockingId=8f3e428b-c281-4f33-9395-85c40b1fd12b1", true, null)
//                        mainPlayer.backButton.visibility = View.GONE
//                        mainPlayer.fullscreenButton.visibility = View.GONE
//                        mainPlayer.startPlayLogic()
//                    }
//                }else{
//                    mainPlayer.visibility = View.GONE
//                    if(!bannerPlayer?.isInPlayingState!!){
//                        bannerPlayer?.startPlayLogic()
//                     }
//
//                }
                lastScrollY = scrollY
                if (scrollY == 0) {
                    mBinding.includeToolbar.ivShare.setImageResource(R.mipmap.iv_share_white)
                    mBinding.includeToolbar.ivShare.setBackgroundResource(R.drawable.round_black_background)
                    mBinding.includeToolbar.ivMessage.setImageResource(R.mipmap.iv_message_white)
                    mBinding.includeToolbar.ivMessage.setBackgroundResource(R.drawable.round_black_background)
                    mBinding.includeToolbar.ivBack.setImageResource(R.mipmap.iv_back_white)
                    mBinding.includeToolbar.ivBack.setBackgroundResource(R.drawable.round_black_background)
                    StatusBarUtil.darkMode(this@GoodsDetailActivity, false)
                } else {
                    mBinding.includeToolbar.ivShare.setImageResource(R.mipmap.iv_share_black)
                    mBinding.includeToolbar.ivShare.setBackgroundResource(0)
                    mBinding.includeToolbar.ivMessage.setImageResource(R.mipmap.iv_message_black)
                    mBinding.includeToolbar.ivMessage.setBackgroundResource(0)
                    mBinding.includeToolbar.ivBack.setImageResource(R.mipmap.iv_back_black)
                    mBinding.includeToolbar.ivBack.setBackgroundResource(0)
                    StatusBarUtil.darkMode(this@GoodsDetailActivity, true)
                }
                when {
                    lastScrollY + 500 < mBinding.includeComment.layoutComment.top -> {
                        mBinding.includeToolbar.radioTabs.check(R.id.rbGoods)
                        setRadioButtonStyle(mBinding.includeToolbar.rbGoods)
                    }
                    lastScrollY + mBinding.includeToolbar.toolbar.height > mBinding.includeComment.layoutComment.top
                            && lastScrollY + mBinding.includeToolbar.toolbar.height < mBinding.includeDetail.layoutDetail.top -> {
                        mBinding.includeToolbar.radioTabs.check(R.id.rbComment)
                        setRadioButtonStyle(mBinding.includeToolbar.rbComment)
                    }
                    lastScrollY + mBinding.includeToolbar.toolbar.height > mBinding.includeDetail.layoutDetail.top 
                            && lastScrollY + mBinding.includeToolbar.toolbar.height < mBinding.tvRecommend.top -> {
                        mBinding.includeToolbar.radioTabs.check(R.id.rbDetail)
                        setRadioButtonStyle(mBinding.includeToolbar.rbDetail)
                    }
                    lastScrollY + mBinding.includeToolbar.toolbar.height > mBinding.tvRecommend.top -> {
                        mBinding.includeToolbar.radioTabs.check(R.id.rbRecommend)
                        setRadioButtonStyle(mBinding.includeToolbar.rbRecommend)
                    }
                }
            }
        })
        mBinding.includeToolbar.rbGoods.setOnClickListener {
            mBinding.svDetail.scrollTo(0, mBinding.includeGoods.layoutGoods.top - mBinding.includeToolbar.toolbar.height)
            setRadioButtonStyle(mBinding.includeToolbar.rbGoods)
        }
        mBinding.includeToolbar.rbComment.setOnClickListener {
            mBinding.svDetail.scrollTo(0, mBinding.includeComment.layoutComment.top - mBinding.includeToolbar.toolbar.height)
            setRadioButtonStyle(mBinding.includeToolbar.rbComment)
        }
        mBinding.includeToolbar.rbDetail.setOnClickListener {
            mBinding.svDetail.scrollTo(0, mBinding.includeDetail.layoutDetail.top - mBinding.includeToolbar.toolbar.height)
            setRadioButtonStyle(mBinding.includeToolbar.rbDetail)
        }
        mBinding.includeToolbar.rbRecommend.setOnClickListener {
            mBinding.svDetail.scrollTo(0, mBinding.tvRecommend.top - mBinding.includeToolbar.toolbar.height)
            setRadioButtonStyle(mBinding.includeToolbar.rbRecommend)
        }
        mBinding.includeComment.tvSeeAllComment.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.push_left_in,
                R.anim.push_left_out
            )
            mCommentFragment = GoodsCommentFragment.newInstance(10)
            transaction.add(R.id.frameComment, mCommentFragment!!)
            transaction.commit()
            mBinding.includeToolbar.tvTitleComment.visible()
            mBinding.includeToolbar.radioTabs.gone()
        }
    }

    private fun closeCommentFragment(){
        mBinding.includeToolbar.tvTitleComment.gone()
        mBinding.includeToolbar.radioTabs.visible()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.push_left_in,
            R.anim.push_left_out
        )
        transaction.remove(mCommentFragment!!)
        transaction.commit()
    }

    private fun setRadioButtonStyle(selectedRB: RadioButton){
        mBinding.includeToolbar.rbGoods.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        mBinding.includeToolbar.rbGoods.setCompoundDrawables(null, null, null, null)
        mBinding.includeToolbar.rbComment.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        mBinding.includeToolbar.rbComment.setCompoundDrawables(null, null, null, null)
        mBinding.includeToolbar.rbDetail.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        mBinding.includeToolbar.rbDetail.setCompoundDrawables(null, null, null, null)
        mBinding.includeToolbar.rbRecommend.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        mBinding.includeToolbar.rbRecommend.setCompoundDrawables(null, null, null, null)

        // 使用代码设置drawableTop
        val drawable = resources.getDrawable(R.mipmap.red_o_line)
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        selectedRB.setCompoundDrawables(null, null, null, drawable)
        selectedRB.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

    }

    private fun initBannerData() {
        mBinding.includeGoods.banner.addBannerLifecycleObserver(this)
        mBinding.includeGoods.banner.indicator = NumIndicator(this)
        mBinding.includeGoods.banner.isAutoLoop(false)
        mBinding.includeGoods.banner.adapter = GoodsBannerAdapter(this, GoodsBannerEntity.getTestData())
        mBinding.includeGoods.banner.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (bannerPlayer == null) {
                    val viewHolder = mBinding.includeGoods.banner.adapter.viewHolder
                    if (viewHolder is GoodsBannerAdapter.VideoHolder) {
                        bannerPlayer = viewHolder.player
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }

    private fun setCommentData(list: List<CommentEntity>){
        Glide.with(this).load(list[0].userIcon).into(mBinding.includeComment.ivCommentHeader)
        mBinding.includeComment.tvCommentUser.text = list[0].userName
        mBinding.includeComment.tvCommentDate.text = list[0].date
        mBinding.includeComment.tvComment.text = list[0].context

        val lp = LinearLayout.LayoutParams(
            (UIUtils.getScreenWidth() - UIUtils.dp2px(32f)) / 4,
            (UIUtils.getScreenWidth() - UIUtils.dp2px(
                32f
            )) / 4
        )
        lp.setMargins(UIUtils.dp2px(2f), UIUtils.dp2px(2f), UIUtils.dp2px(2f), UIUtils.dp2px(2f))
        list[0].pics.forEach {
            val image = ImageView(this)
            image.layoutParams = lp
            Glide.with(this).load(it.url).into(image)
            mBinding.includeComment.layoutPics.addView(image)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(mCommentFragment!=null && mCommentFragment!!.isAdded){
                closeCommentFragment()
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onPause() {
        super.onPause()
        bannerPlayer?.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        bannerPlayer?.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    override fun onBackPressed() {
        //释放所有
        bannerPlayer?.setVideoAllCallBack(null)
        super.onBackPressed()
    }

    companion object{
        fun launch(activity: FragmentActivity, goodsName: String) =
            activity.apply {
                val intent = Intent(this, GoodsDetailActivity::class.java)
                intent.putExtra("goods_name", goodsName)
                startActivity(intent)
            }
    }
}