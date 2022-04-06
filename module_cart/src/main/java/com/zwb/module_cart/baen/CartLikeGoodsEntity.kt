package com.zwb.module_cart.baen

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_cart.adapter.CartAdapter

data class CartLikeGoodsEntity(var goodsID:Int,
                               var goodsName:String,
                               var goodsModel:String,
                               var price:Double,
                               var oldprice:Double,
                               var saleDiscount:Double,
                               var picURL:String):MultiItemEntity{
    override fun getItemType(): Int {
        return  CartAdapter.GRID_DATA
    }

}