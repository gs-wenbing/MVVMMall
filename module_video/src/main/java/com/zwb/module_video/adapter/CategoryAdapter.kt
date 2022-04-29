package com.zwb.module_video.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.dp2px
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.R
import com.zwb.module_video.bean.CategoryEntity

/**
 * 热门分类和专题策划共用
 */
class CategoryAdapter(list: MutableList<CategoryEntity>) :
    BaseQuickAdapter<CategoryEntity, BaseViewHolder>(R.layout.adapter_category_layout, list) {

    override fun convert(helper: BaseViewHolder, item: CategoryEntity?) {
        item?.let {
            helper.setText(R.id.tvCategory, it.title)
            val ivCategoryBg = helper.getView<ImageView>(R.id.ivCategoryBg)
            helper.itemView.width((UIUtils.getScreenWidth() - mContext.dp2px(8f)) / 3)
            BannerUtils.setBannerRound(ivCategoryBg, 20f)
            Glide.with(ivCategoryBg.context)
                .load(it.image)
                .into(ivCategoryBg)
        }
    }
}