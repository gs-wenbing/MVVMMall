package com.zwb.mvvm_mall.bean

data class GoodsEntity(var goodsID:Int,
                       var goodsName:String,
                       var goodsModel:String,
                       var price:Double,
                       var oldprice:Double,
                       var saleDiscount:Double,
                       var picURL:String)