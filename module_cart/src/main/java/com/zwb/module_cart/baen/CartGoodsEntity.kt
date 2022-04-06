package com.zwb.module_cart.baen

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_cart.adapter.CartAdapter

data class CartGoodsEntity(var goodsID:Int,
                           var goodsName:String,
                           var goodsModel:String,
                           var price:Double,
                           var stock:Int,
                           var cartNum:Int,
                           var picURL:String,
                           var modelList:List<GoodsModelEntity>,
                           var serviceList:List<GoodsServiceEntity>,
                           var isSelected:Boolean):MultiItemEntity{
    override fun getItemType(): Int {
        return CartAdapter.LINEAR_DATA
    }

}