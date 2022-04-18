package com.zwb.module_home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_home.adapter.SeckillGoodsAdapter

data class SeckillMoreEntity(var title:String): MultiItemEntity {
    override fun getItemType(): Int {
        return SeckillGoodsAdapter.MORE
    }
}