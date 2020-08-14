package com.zwb.mvvm_mall.bean

data class GoodsServiceEntity(var securityTitle:String,
                             var securitySubTitle:String,
                             var securityList:List<SecurityEntity>)


data class SecurityEntity(var securityLimit:String,
                         var securityPrice:Double)