package com.zwb.module_goods.adapter

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_goods.R

class HomeGoodsAdapter(data: MutableList<GoodsEntity>?, resID:Int = R.layout.item_goods_common) :
    BaseQuickAdapter<GoodsEntity, BaseViewHolder>(resID, data) {

    private var priceSize= UIUtils.dp2px(16f)

    fun setPriceSize(size:Int){
        priceSize=UIUtils.dp2px(size.toFloat())
    }
    override fun convert(helper: BaseViewHolder, item: GoodsEntity?) {
        item?.let {
            helper.setText(R.id.tvGoodsName,it.goodsName)
            Glide.with(mContext).load(it.picURL).into(helper.getView(R.id.ivIcon))

            val price = String.format(mContext.getString(R.string.price),it.price.toString())
            helper.setText(R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(price,
                AbsoluteSizeSpan(priceSize),
                ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.colorPrimary)),
                2,price.indexOf(".")+1
            ))
        }
    }

}