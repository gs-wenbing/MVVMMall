package com.zwb.module_cart.baen

data class GoodsModelEntity(var attrTitle:String,
                            var attrList:List<GoodsModelItemEntity>)

data class GoodsModelItemEntity(var attrName:String)