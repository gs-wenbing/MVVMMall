package com.zwb.mvvm_mall.ui.home.adapter

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.common.utils.UIUtils

class HomeGoodsAdapter(data: MutableList<GoodsEntity>?,resID:Int = R.layout.item_goods_commen_layout) :
    BaseQuickAdapter<GoodsEntity, BaseViewHolder>(resID, data) {

    override fun convert(helper: BaseViewHolder, item: GoodsEntity?) {
        item?.let {
            helper.setText(R.id.tvGoodsName,it.goodsName)
            Glide.with(mContext).load(it.picURL).into(helper.getView(R.id.ivIcon))

            val price = String.format(mContext.getString(R.string.price),it.price.toString())
            helper.setText(R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(price,
                AbsoluteSizeSpan(UIUtils.dp2px(18f)),
                ForegroundColorSpan(mContext.getColor(R.color.mainRed)),
                2,price.indexOf(".")+1
            ))
        }
    }

}