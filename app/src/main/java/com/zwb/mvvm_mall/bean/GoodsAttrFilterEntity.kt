package com.zwb.mvvm_mall.bean

data class GoodsAttrFilterEntity(var attrName:String,
                                 var selectString:String,
                                 var isSelected:Boolean,
                                 var subAttrList:List<GoodsAttrFilterEntity>?)