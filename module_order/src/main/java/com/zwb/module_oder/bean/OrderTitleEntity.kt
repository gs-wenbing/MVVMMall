package com.zwb.module_oder.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_oder.adapter.OrderAdapter

data class OrderTitleEntity(
    var orderID: Long,
    var shopID: Long,
    var shopName: String,
    var shopIcon: String,
    var orderStatus: Int,
    var isSelected: Boolean = false
) : MultiItemEntity {

    override fun getItemType(): Int {
        return OrderAdapter.TITLE
    }

    override fun toString(): String {
        return this.shopName
    }
//    override fun equals(other: Any?): Boolean {
//        return if(other is OrderTitleEntity){
//            this.orderID == other.orderID
//        }else{
//            super.equals(other)
//        }
//    }
//
//    override fun hashCode(): Int {
//        var result = orderID.hashCode()
//        result = 31 * result + shopID.hashCode()
//        result = 31 * result + shopName.hashCode()
//        result = 31 * result + shopIcon.hashCode()
//        result = 31 * result + orderStatus
//        return result
//    }
}