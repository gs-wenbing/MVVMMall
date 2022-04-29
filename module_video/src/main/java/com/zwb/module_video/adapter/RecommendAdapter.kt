package com.zwb.module_video.adapter

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.R
import com.zwb.module_video.bean.TitleEntity
import com.zwb.module_video.bean.VideoEntity
import com.zwb.module_video.bean.VideoSmallEntity

class RecommendAdapter(var fragment: Fragment, data: MutableList<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(AdapterItemType.BANNER_VIEW, R.layout.item_banner_layout)
        addItemType(AdapterItemType.TITLE_VIEW, R.layout.item_title_layout)
        addItemType(AdapterItemType.VIDEO_SMALL_CARD_VIEW, R.layout.item_video_small_layout)
        addItemType(AdapterItemType.VIDEO_CARD_VIEW, R.layout.item_video_layout)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            AdapterItemType.TITLE_VIEW -> {
                item?.let {
                    val data = it as TitleEntity
                    val titleView = helper.getView<SuperTextView>(R.id.titleView)
                    titleView.setLeftString(data.text)
                    titleView.setLeftTextIsBold(true)
                    titleView.setRightString(data.rightText)
                }
            }
            AdapterItemType.VIDEO_SMALL_CARD_VIEW -> {
                item?.let {
                    val data = it as VideoSmallEntity
                    helper.setText(R.id.tv_video_time, DateUtils.format(data.videoTime))
                    helper.setText(R.id.tv_video_title,data.title)
                    helper.setText(R.id.description,data.description)
                    val ivVideoCover = helper.getView<ImageView>(R.id.iv_video_cover)
                    BannerUtils.setBannerRound(ivVideoCover, 20f)
                    Glide.with(ivVideoCover.context)
                        .load(data.coverUrl)
                        .into(ivVideoCover)
                }
            }
            AdapterItemType.VIDEO_CARD_VIEW -> {
                item?.let {
                    val data = it as VideoEntity
                    helper.setText(R.id.tv_video_time,DateUtils.format(data.videoTime))
                    val ivVideoCover = helper.getView<ImageView>(R.id.iv_video_cover)
                    BannerUtils.setBannerRound(ivVideoCover, 20f)
                    Glide.with(ivVideoCover.context)
                        .load(data.coverUrl)
                        .into(ivVideoCover)

                    val ivAuthor = helper.getView<ImageView>(R.id.iv_author)
                    BannerUtils.setBannerRound(ivAuthor, UIUtils.dp2px(40f).toFloat())
                    Glide.with(ivAuthor.context)
                        .load(data.authorIcon)
                        .into(ivAuthor)

                    helper.setText(R.id.tv_title,data.title)
                    helper.setText(R.id.tv_description,data.description)
                }
            }
        }
    }
}