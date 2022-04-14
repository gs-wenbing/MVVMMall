package com.zwb.module_cart.baen

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_cart.adapter.CartAdapter

data class CartEmptyEntity(var title:String):MultiItemEntity{
    override fun getItemType(): Int {
        return CartAdapter.NO_DATA
    }
}