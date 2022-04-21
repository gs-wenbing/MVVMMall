package com.zwb.module_oder.bean

data class OrderEntity(
    var id: Long,
    var shopID: Long,
    var shopName: String,
    var shopIcon: String,
    var orderStatus: Int,
    var orderNo: String,
    var orderTime: String,
    var goodsList: List<OrderGoodsEntity>,
    var addressInfo: OrderAddressEntity
) 