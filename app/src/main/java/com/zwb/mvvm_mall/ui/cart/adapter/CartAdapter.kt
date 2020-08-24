package com.zwb.mvvm_mall.ui.cart.adapter

import android.content.res.ColorStateList
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.CartDividingEntity
import com.zwb.mvvm_mall.bean.CartGoodsEntity
import com.zwb.mvvm_mall.bean.CartLikeGoodsEntity
import com.zwb.mvvm_mall.common.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_mine.*


class CartAdapter(data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {


    init {
        addItemType(STRING_DATA, R.layout.item_cart_dividing_layout)
        addItemType(LINEAR_DATA, R.layout.item_cart_linear_layout)
        addItemType(GRID_DATA, R.layout.item_cart_goods_layout)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        when (helper.itemViewType) {
            STRING_DATA -> {
                item?.let {
                    val cartDividing = it as CartDividingEntity
                    helper.setText(R.id.tvCartLike,cartDividing.title)
                    setViewStyle(helper)
                }
            }
            LINEAR_DATA -> {
                item?.let {
                    val cartGoods = it as CartGoodsEntity
                    Glide.with(mContext).load(cartGoods.picURL).into(helper.getView(R.id.ivIcon))
                    helper.setText(R.id.tvGoodsName,cartGoods.goodsName)
                    helper.setText(R.id.tvGoodsModel,cartGoods.goodsModel)
                    helper.setText(R.id.cartNum,cartGoods.cartNum.toString())

                    val price = String.format(mContext.getString(R.string.price),cartGoods.price.toString())
                    helper.setText(R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(price,
                        AbsoluteSizeSpan(UIUtils.dp2px(18f)),
                        ForegroundColorSpan(mContext.getColor(R.color.mainRed)),
                        2,price.indexOf(".")+1
                    ))
                    if(cartGoods.isSelected){
                        (helper.getView(R.id.ivCheck) as ImageView).setImageResource(R.mipmap.checkbox_checked)
                    }else{
                        (helper.getView(R.id.ivCheck) as ImageView).setImageResource(R.mipmap.checkbox)
                    }
                    helper.addOnClickListener(R.id.ivIcon)
                    helper.addOnClickListener(R.id.tvGoodsName)
                    helper.addOnClickListener(R.id.ivCheck)
                    helper.addOnClickListener(R.id.tvGoodsModel)
                    helper.addOnClickListener(R.id.ivReduce)
                    helper.addOnClickListener(R.id.ivPlus)
                }
            }
            GRID_DATA -> {
                item?.let {
                    val cartLikeGoods = it as CartLikeGoodsEntity
                    Glide.with(mContext).load(cartLikeGoods.picURL).into(helper.getView(R.id.ivIcon))
                    helper.setText(R.id.tvGoodsName,cartLikeGoods.goodsName)
                    val price = String.format(mContext.getString(R.string.price),cartLikeGoods.price.toString())
                    helper.setText(R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(price,
                        AbsoluteSizeSpan(UIUtils.dp2px(18f)),
                        ForegroundColorSpan(mContext.getColor(R.color.mainRed)),
                        1,price.indexOf(".")+1
                    ))
                }
            }
        }
    }

    private fun setViewStyle(helper: BaseViewHolder){
        val cloud1 = helper.getView<ImageView>(R.id.cloud1)
        helper.getView<ImageView>(R.id.cloud1).setImageDrawable(
            UIUtils.tintDrawable(
                cloud1.drawable,
                ColorStateList.valueOf(mContext.getColor(R.color.F5766F))
            )
        )
        val curve1 = helper.getView<ImageView>(R.id.curve1)
        curve1.setImageDrawable(
            UIUtils.tintDrawable(
                curve1.drawable,
                ColorStateList.valueOf(mContext.getColor(R.color.F5766F))
            )
        )

        val cloud2 = helper.getView<ImageView>(R.id.cloud2)
        cloud2.setImageDrawable(
            UIUtils.tintDrawable(
                cloud2.drawable,
                ColorStateList.valueOf(mContext.getColor(R.color.E0C675))
            )
        )
        val curve2 = helper.getView<ImageView>(R.id.curve2)
        curve2.setImageDrawable(
            UIUtils.tintDrawable(
                curve2.drawable,
                ColorStateList.valueOf(mContext.getColor(R.color.E0C675))
            )
        )

        UIUtils.setTextViewStyles(
            helper.getView(R.id.tvCartLike),
            intArrayOf(
                mContext.getColor(R.color.F5766F),
                mContext.getColor(R.color.mainRed),
                mContext.getColor(R.color.F5766F)
            ), floatArrayOf(0f, 0.5f, 1.0f)
        )
    }
    companion object{
        const val STRING_DATA = 0
        const val LINEAR_DATA = 1
        const val GRID_DATA = 2
    }

}