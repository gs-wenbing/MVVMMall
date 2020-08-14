package com.zwb.mvvm_mall.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.mvvm_mall.ui.cart.adapter.CartAdapter

data class CartDividingEntity(var title:String):MultiItemEntity{
    override fun getItemType(): Int {
        return CartAdapter.STRING_DATA
    }
}