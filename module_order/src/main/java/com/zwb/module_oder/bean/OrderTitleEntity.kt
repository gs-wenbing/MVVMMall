package com.zwb.module_oder.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_oder.adapter.OrderAdapter

data class OrderTitleEntity(
    var shopID: Long,
    var shopName: String,
    var shopIcon: String,
    var orderStatus: Int,
) : MultiItemEntity {

    override fun getItemType(): Int {
        return OrderAdapter.TITLE
    }
}