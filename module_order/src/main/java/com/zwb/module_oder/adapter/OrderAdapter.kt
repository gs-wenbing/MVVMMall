package com.zwb.module_oder.adapter

import android.graphics.Typeface
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_oder.R
import com.zwb.module_oder.bean.OrderEntity
import com.zwb.module_oder.bean.OrderTitleEntity

class OrderAdapter (data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(TITLE, R.layout.item_order_title)
        addItemType(DATA, R.layout.item_order_goods)
        addItemType(OPERATE, R.layout.item_order_operate)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            TITLE-> item?.let {
                val orderTitle = it as OrderTitleEntity
                val txtItem = helper.getView<SuperTextView>(R.id.txtItem)
                txtItem.setLeftString(orderTitle.shopName)
                txtItem.leftTextView.setTypeface(null, Typeface.BOLD)
            }
            DATA -> item?.let {
                val order = it as OrderEntity
                val txtItem = helper.getView<SuperTextView>(R.id.txtItem)
                txtItem.leftTopTextView.ellipsize = TextUtils.TruncateAt.END
                val price = String.format(mContext.getString(R.string.price), order.price)
                txtItem.rightTopTextView.text = UIUtils.setTextViewContentStyle(price,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.mainBlack)),
                    2,price.indexOf(".")+1
                )
                txtItem.leftBottomTextView.setBackgroundResource(R.drawable.btn_common_hollow_nor)
                txtItem.leftBottomTextView.setPadding(8,0,8,0)
                BannerUtils.setBannerRound(txtItem.leftIconIV, 10f)
                Glide.with(mContext).load(order.picURL).into(txtItem.leftIconIV)
            }
            OPERATE -> item?.let {
                val tvRealPayAmt = helper.getView<TextView>(R.id.tvRealPayAmt)
                tvRealPayAmt.setTypeface(null, Typeface.BOLD)
                val price = String.format(mContext.getString(R.string.real_pay_amt),"3434.23")
                tvRealPayAmt.text = UIUtils.setTextViewContentStyle(price,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.mainBlack)),
                    5,price.indexOf(".")+1
                )
            }
        }
    }

    companion object{
        const val TITLE = 0
        const val DATA = 1
        const val OPERATE = 2
    }
}