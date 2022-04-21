package com.zwb.module_oder.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_oder.adapter.OrderAdapter

data class OrderOperateEntity(
    var orderID: Long,
    var orderStatus: Int,
    var realPayAmt: Double
) : MultiItemEntity {

    override fun getItemType(): Int {
        return OrderAdapter.OPERATE
    }
}