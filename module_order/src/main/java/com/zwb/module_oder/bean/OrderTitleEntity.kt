package com.zwb.module_oder.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_oder.adapter.OrderAdapter

class OrderTitleEntity(status: Int, var shopName: String) : OrderEntity(status), MultiItemEntity {

    override fun getItemType(): Int {
        return OrderAdapter.TITLE
    }
}