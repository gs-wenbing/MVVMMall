package com.zwb.module_video.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.R
import com.zwb.module_video.bean.CommunitySquareEntity

class SquareAdapter(list: MutableList<CommunitySquareEntity>) :
    BaseQuickAdapter<CommunitySquareEntity, BaseViewHolder>(R.layout.adapter_community_square_layout, list) {

    override fun convert(helper: BaseViewHolder, item: CommunitySquareEntity?) {
        item?.let {
            helper.setText(R.id.tv_title, it.title)
            helper.setText(R.id.tv_subtitle, it.subTitle)
            val ivCover = helper.getView<ImageView>(R.id.iv_cover_bg)
            helper.itemView.width((UIUtils.getScreenWidth() - UIUtils.dp2px(24f)) / 2)
            BannerUtils.setBannerRound(ivCover, 20f)
            Glide.with(ivCover.context)
                .load(it.bgPicture)
                .into(ivCover)
        }
    }
}