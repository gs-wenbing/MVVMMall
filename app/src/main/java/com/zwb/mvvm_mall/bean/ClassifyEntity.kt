package com.zwb.mvvm_mall.bean

data class ClassifyEntity(var goodsClassID:Int,
                          var parentID:Int,
                          var goodsClassName:String,
                          var picURL:String?,
                          var children:List<ClassifyEntity>?,
                          var isSelected:Boolean)