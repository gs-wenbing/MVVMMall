package com.zwb.module_video.adapter

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.R
import com.zwb.module_video.bean.ReplyEntity
import com.zwb.module_video.bean.TitleEntity
import com.zwb.module_video.bean.VideoSmallEntity

class VideoDetailAdapter(data: MutableList<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(AdapterItemType.TITLE_VIEW, R.layout.item_title_layout)
        addItemType(AdapterItemType.VIDEO_SMALL_CARD_VIEW, R.layout.item_video_small_layout)
        addItemType(AdapterItemType.REPLY_VIEW, R.layout.item_video_reply_layout)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            AdapterItemType.TITLE_VIEW -> {
                item?.let {
                    val data = it as TitleEntity
                    val titleView = helper.getView<SuperTextView>(R.id.titleView)
                    titleView.setLeftString(data.text)
                    titleView.setLeftTextIsBold(true)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        titleView.leftTextView.setTextColor(
                            mContext.resources.getColor(
                                R.color.main_grey,
                                null
                            )
                        )
                    }
                    titleView.rightTextView.gone()
                }
            }
            AdapterItemType.VIDEO_SMALL_CARD_VIEW -> {
                item?.let {
                    val data = it as VideoSmallEntity
                    helper.setText(R.id.tv_video_time, DateUtils.format(data.videoTime))
                    val tvVideoTitle = helper.getView<TextView>(R.id.tv_video_title)
                    tvVideoTitle.text = data.title
                    val tvDescription = helper.getView<TextView>(R.id.description)
                    tvDescription.text = data.description
                    //设置字体颜色和样式
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        tvVideoTitle.setTextColor(
                            mContext.resources.getColor(
                                R.color.main_grey,
                                null
                            )
                        )
                        tvDescription.setTextColor(
                            mContext.resources.getColor(
                                R.color.main_grey,
                                null
                            )
                        )
                        tvDescription.typeface = Typeface.DEFAULT
                    }
                    helper.setImageResource(R.id.iv_share, R.mipmap.iv_share_white)
                    val ivVideoCover = helper.getView<ImageView>(R.id.iv_video_cover)
                    BannerUtils.setBannerRound(ivVideoCover, 20f)
                    Glide.with(ivVideoCover.context)
                        .load(data.coverUrl)
                        .into(ivVideoCover)
                }
            }
            AdapterItemType.REPLY_VIEW -> {

                item?.let {
                    val data = it as ReplyEntity

                    val ivAuthor = helper.getView<ImageView>(R.id.iv_user_avatar)
                    BannerUtils.setBannerRound(ivAuthor, UIUtils.dp2px(30f).toFloat())
                    Glide.with(ivAuthor.context)
                        .load(data.avatar)
                        .into(ivAuthor)

                    helper.setText(R.id.tv_user_name, data.nickName)
                    helper.setText(R.id.tv_user_message, data.replyMessage)
                    helper.setText(R.id.tv_user_name, data.nickName)
                    helper.setText(
                        R.id.tv_user_release_time, "发布于 " + DateUtils.getDate(
                            data.releaseTime.toString(),
                            "HH:mm"
                        )
                    )
                    helper.setText(R.id.tv_like_count, data.likeCount.toString())
                }
            }

        }
    }
}