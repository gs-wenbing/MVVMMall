package com.zwb.module_goods.bean

data class GoodsAttrFilterEntity(var attrName:String,
                                 var selectString:String,
                                 var isSelected:Boolean,
                                 var subAttrList:List<GoodsAttrFilterEntity>?)