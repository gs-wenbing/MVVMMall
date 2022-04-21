package com.zwb.module_oder.adapter

import android.graphics.Typeface
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.allen.library.SuperTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.constant.Constants
import com.zwb.module_oder.R
import com.zwb.module_oder.bean.OrderEntity
import com.zwb.module_oder.bean.OrderGoodsEntity
import com.zwb.module_oder.bean.OrderOperateEntity
import com.zwb.module_oder.bean.OrderTitleEntity

class OrderAdapter(
    data: MutableList<MultiItemEntity>?,
    var tabStatus: Int = Constants.Order.ORDER_ALL
) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(TITLE, R.layout.item_order_title)
        addItemType(DATA, R.layout.item_order_goods)
        addItemType(OPERATE, R.layout.item_order_operate)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            TITLE -> item?.let {
                val orderTitle = it as OrderTitleEntity
                val ivCheck = helper.getView<ImageView>(R.id.ivCheck)
                when (tabStatus) {
                    Constants.Order.ORDER_NOT_PAY -> {
                        ivCheck.visible()
                        if (orderTitle.isSelected) {
                            ivCheck.setImageResource(R.mipmap.checkbox_checked)
                        } else {
                            ivCheck.setImageResource(R.mipmap.checkbox)
                        }
                    }
                    else -> ivCheck.gone()
                }
                val txtItem = helper.getView<SuperTextView>(R.id.txtItem)
                txtItem.setLeftString(orderTitle.shopName)
                txtItem.leftTextView.id = R.id.order_shop
                txtItem.leftTextView.setTypeface(null, Typeface.BOLD)
                when (orderTitle.orderStatus) {
                    Constants.Order.ORDER_NOT_PAY -> txtItem.rightTextView.text = "等待买家付款"
                    Constants.Order.ORDER_CLOSE -> txtItem.rightTextView.text = "交易关闭"
                    Constants.Order.ORDER_NOT_SENT -> txtItem.rightTextView.text = "等待卖家发货"
                    Constants.Order.ORDER_NOT_RECEIVE -> txtItem.rightTextView.text = "配送中"
                    Constants.Order.ORDER_NOT_COMMENT,
                    Constants.Order.ORDER_SUCCESS -> txtItem.rightTextView.text = "交易成功"
                }
                Glide.with(mContext).load(orderTitle.shopIcon).placeholder(R.mipmap.iv_shop)
                    .into(txtItem.leftIconIV)
                helper.addOnClickListener(R.id.order_shop, R.id.ivCheck)
            }
            DATA -> item?.let {
                val order = it as OrderGoodsEntity
                val txtItem = helper.getView<SuperTextView>(R.id.txtItem)
                txtItem.leftTopTextView.ellipsize = TextUtils.TruncateAt.END
                val price = String.format(mContext.getString(R.string.price), order.price)
                txtItem.rightTopTextView.text = UIUtils.setTextViewContentStyle(
                    price,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.mainBlack)),
                    2, price.indexOf(".") + 1
                )
                txtItem.rightTextView.text =
                    String.format(mContext.getString(R.string.num), order.num)
                txtItem.leftBottomTextView.setBackgroundResource(R.drawable.btn_common_hollow_nor)
                txtItem.leftBottomTextView.setPadding(8, 0, 8, 0)
                BannerUtils.setBannerRound(txtItem.leftIconIV, 10f)
                Glide.with(mContext).load(order.picURL).into(txtItem.leftIconIV)
            }
            OPERATE -> item?.let {
                val order = it as OrderOperateEntity
                val tvRealPayAmt = helper.getView<TextView>(R.id.tvRealPayAmt)
                tvRealPayAmt.setTypeface(null, Typeface.BOLD)
                val price =
                    String.format(mContext.getString(R.string.real_pay_amt), order.realPayAmt)
                tvRealPayAmt.text = UIUtils.setTextViewContentStyle(
                    price,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.mainBlack)),
                    5, price.indexOf(".") + 1
                )
                helper.addOnClickListener(R.id.tvHandle1, R.id.tvHandle2, R.id.tvHandle3)
                val tvHandle1 = helper.getView<TextView>(R.id.tvHandle1)
                val tvHandle2 = helper.getView<TextView>(R.id.tvHandle2)
                val tvHandle3 = helper.getView<TextView>(R.id.tvHandle3)
                tvHandle2.gone()
                tvHandle3.gone()
                when (order.orderStatus) {
                    Constants.Order.ORDER_NOT_PAY -> {
                        tvHandle1.text = "付款"
                        tvHandle2.text = "找朋友付"
                        tvHandle3.text = "修改地址"
                        tvHandle2.visible()
                        tvHandle3.visible()
                    }
                    Constants.Order.ORDER_NOT_SENT -> {
                        tvHandle1.text = "提醒发货"
                        tvHandle2.text = "加入购物车"
                        tvHandle2.visible()
                    }
                    Constants.Order.ORDER_NOT_RECEIVE -> {
                        tvHandle1.text = "查看物流"
                        tvHandle2.text = "加入购物车"
                        tvHandle2.visible()
                    }
                    Constants.Order.ORDER_NOT_COMMENT,
                    Constants.Order.ORDER_SUCCESS -> {
                        tvHandle1.text = "评价"
                        tvHandle2.text = "查看物流"
                        tvHandle3.text = "加入购物车"
                        tvHandle2.visible()
                        tvHandle3.visible()
                    }
                    Constants.Order.ORDER_CLOSE -> {
                        tvHandle1.text = "加入购物车"
                        tvHandle2.text = "删除订单"
                        tvHandle2.visible()
                    }

                }
            }
        }
    }

    fun setTab(t: Int) {
        tabStatus = t
    }

    companion object {
        const val TITLE = 0
        const val DATA = 1
        const val OPERATE = 2
    }
}