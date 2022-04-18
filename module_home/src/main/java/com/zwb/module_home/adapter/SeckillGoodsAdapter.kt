package com.zwb.module_home.adapter

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.dp2px
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_home.R
import com.zwb.module_home.bean.SeckillGoodsEntity

class SeckillGoodsAdapter(data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(NORMAL, R.layout.item_goods_small_layout)
        addItemType(MORE, R.layout.item_horizontal_more)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            MORE -> item?.let {
                helper.addOnClickListener(R.id.tvMore)
            }
            NORMAL -> item?.let {
                val seckillGoods = it as SeckillGoodsEntity
                helper.itemView.width((UIUtils.getScreenWidth() - mContext.dp2px(20f)) / 4)
                helper.setText(R.id.tvGoodsName, seckillGoods.goodsName)
                Glide.with(mContext).load(seckillGoods.picURL).into(helper.getView(R.id.ivIcon))

                val price =
                    String.format(mContext.getString(R.string.price), seckillGoods.price.toString())

                helper.setText(
                    R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(
                        price,
                        AbsoluteSizeSpan(mContext.dp2px(14f)),
                        ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)),
                        2, price.indexOf(".") + 1
                    )
                )
            }
        }
    }

    companion object {
        const val MORE = 0
        const val NORMAL = 1
    }


}