package com.zwb.module_video.adapter

import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.R
import com.zwb.module_video.adapter.AdapterItemType.BANNER_VIEW
import com.zwb.module_video.adapter.AdapterItemType.CATEGORY_CARD_VIEW
import com.zwb.module_video.adapter.AdapterItemType.THEME_CARD_VIEW
import com.zwb.module_video.adapter.AdapterItemType.TITLE_VIEW
import com.zwb.module_video.adapter.AdapterItemType.VIDEO_SMALL_CARD_VIEW
import com.zwb.module_video.bean.*

class DiscoverAdapter2(var fragment: Fragment, data: MutableList<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {
    init {
        addItemType(BANNER_VIEW, R.layout.item_banner_layout)
        addItemType(TITLE_VIEW, R.layout.item_title_layout)
        addItemType(CATEGORY_CARD_VIEW, R.layout.item_category_layout)
        addItemType(VIDEO_SMALL_CARD_VIEW, R.layout.item_video_small_layout)
        addItemType(THEME_CARD_VIEW, R.layout.item_theme_layout)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        Log.e("DiscoverAdapter2",helper.itemViewType.toString())
        when (helper.itemViewType) {
            BANNER_VIEW -> {
                item?.let {
                    val data = it as BannerList
                    val banner: Banner<BannerEntity, BannerVideoAdapter> = helper.getView(R.id.banner)
                    if(banner.adapter == null) banner.adapter = BannerVideoAdapter(mutableListOf())
                    banner.addBannerLifecycleObserver(fragment)
                    banner.indicator = CircleIndicator(fragment.context)
                    banner.setBannerRound2(20f)
                    banner.adapter.setDatas(data.bannerList)
                }
            }
            TITLE_VIEW -> {
                item?.let {
                    val data = it as TitleEntity
                    val titleView = helper.getView<SuperTextView>(R.id.titleView)
                    titleView.setLeftString(data.text)
                    titleView.setLeftTextIsBold(true)
                    titleView.setRightString(data.rightText)
                }
            }
            CATEGORY_CARD_VIEW -> {
                item?.let {
                    val data = it as CategoryList
                    val rvCategory = helper.getView<RecyclerView>(R.id.rvCategory)
                    if(rvCategory.layoutManager == null){
                        val layoutManager = GridLayoutManager(mContext, 2)
                        val categoryAdapter = CategoryAdapter(mutableListOf())
                        layoutManager.orientation = RecyclerView.HORIZONTAL
                        rvCategory.layoutManager = layoutManager
                        rvCategory.adapter = categoryAdapter
                        categoryAdapter.setNewData(data.categoryList)
                    }
                }
            }
            VIDEO_SMALL_CARD_VIEW -> {
                item?.let {
                    val data = it as VideoSmallEntity
                    helper.setText(R.id.tv_video_time,DateUtils.format(data.videoTime))
                    helper.setText(R.id.tv_video_title,data.title)
                    helper.setText(R.id.description,data.description)
                    val ivVideoCover = helper.getView<ImageView>(R.id.iv_video_cover)
                    BannerUtils.setBannerRound(ivVideoCover, 20f)
                    Glide.with(ivVideoCover.context)
                        .load(data.coverUrl)
                        .into(ivVideoCover)
                }
            }
            THEME_CARD_VIEW -> {
                item?.let {
                    val data = it as ThemeEntity
                    val themeView = helper.getView<SuperTextView>(R.id.themeView)
                    themeView.setLeftTopString(data.title)
                    themeView.setLeftBottomString(data.description)
                    themeView.rightTextView.setBackgroundResource(R.drawable.shape_grey_hollow)
                    themeView.rightTextView.setPadding(UIUtils.dp2px(4f),0,UIUtils.dp2px(4f),0)
                    BannerUtils.setBannerRound(themeView.leftIconIV, 20f)
                    Glide.with(themeView.context)
                        .load(data.icon)
                        .into(themeView.leftIconIV)
                }
            }

        }
    }


}

