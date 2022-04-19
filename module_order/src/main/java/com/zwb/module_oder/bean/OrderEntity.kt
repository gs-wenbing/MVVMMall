package com.zwb.module_oder.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_oder.adapter.OrderAdapter

open class OrderEntity(var goodsID:Int = 0,
                       var goodsName:String = "",
                       var goodsModel:String = "",
                       var price:Double = 0.0,
                       var num:Int = 0,
                       var status:Int = 0,
                       var picURL:String = ""): MultiItemEntity {
    override fun getItemType(): Int {
        return OrderAdapter.DATA
    }
}