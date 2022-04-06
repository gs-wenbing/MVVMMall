package com.zwb.module_cart.baen

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_cart.adapter.CartAdapter

data class CartDividingEntity(var title:String):MultiItemEntity{
    override fun getItemType(): Int {
        return CartAdapter.STRING_DATA
    }
}