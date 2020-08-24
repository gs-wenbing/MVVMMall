package com.zwb.mvvm_mall.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class HomeMultiItemEntity(var type:Int) : MultiItemEntity {
    override fun getItemType(): Int {
        return type
    }
}