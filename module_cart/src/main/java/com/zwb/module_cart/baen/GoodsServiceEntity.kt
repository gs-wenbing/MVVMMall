package com.zwb.module_cart.baen

data class GoodsServiceEntity(var securityTitle:String,
                             var securitySubTitle:String,
                             var securityList:List<SecurityEntity>)


data class SecurityEntity(var securityLimit:String,
                         var securityPrice:Double)