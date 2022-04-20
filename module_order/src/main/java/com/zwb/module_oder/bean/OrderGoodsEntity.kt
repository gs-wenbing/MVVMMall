package com.zwb.module_oder.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_oder.adapter.OrderAdapter

data class OrderGoodsEntity(
    var goodsID: Long = 0,
    var goodsName: String = "",
    var goodsModel: String = "",
    var price: Double = 0.0,
    var num: Int = 0,
    var goodsStatus: Int = 0,
    var picURL: String = ""
) : MultiItemEntity {
    override fun getItemType(): Int {
        return OrderAdapter.DATA
    }
}