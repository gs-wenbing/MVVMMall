package com.zwb.mvvm_mall.bean

data class GoodsModelEntity(var attrTitle:String,
                            var attrList:List<GoodsModelItemEntity>)

data class GoodsModelItemEntity(var attrName:String)