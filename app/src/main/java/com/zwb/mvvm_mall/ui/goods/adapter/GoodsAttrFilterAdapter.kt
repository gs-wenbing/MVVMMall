package com.zwb.mvvm_mall.ui.goods.adapter

import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.GoodsAttrFilterEntity



class GoodsAttrFilterAdapter(data: MutableList<GoodsAttrFilterEntity>?) :
    BaseQuickAdapter<GoodsAttrFilterEntity, BaseViewHolder>(R.layout.item_goodslist_attr_filter, data) {

    override fun convert(helper: BaseViewHolder, item: GoodsAttrFilterEntity?) {
        item?.let {
            val tvAttrName = helper.getView<TextView>(R.id.tvAttrName)
            tvAttrName.text = it.attrName
            val drawable = mContext.resources.getDrawable(R.mipmap.down_arrow)
            //设置箭头图标
            if(it.subAttrList==null || it.subAttrList!!.isEmpty()){
                tvAttrName.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null)
            }else{
                tvAttrName.setCompoundDrawablesWithIntrinsicBounds(null,null, drawable, null)
            }
            //选中的状态
            if(it.isSelected){
                tvAttrName.setTextColor(mContext.getColor(R.color.mainRed))
                tvAttrName.typeface = Typeface.DEFAULT_BOLD
                tvAttrName.setBackgroundResource(R.drawable.btn_common_hollow_selector)
                //下拉框选中的值
                if (TextUtils.isEmpty(it.selectString)) {
                    tvAttrName.text = it.attrName
                } else {
                    tvAttrName.text = it.selectString
                }
            }else{
                tvAttrName.setTextColor(mContext.getColor(R.color.grey_text))
                tvAttrName.typeface = Typeface.DEFAULT
                tvAttrName.setBackgroundResource(R.drawable.shape_grey_background)
            }
        }
    }
}