package com.zwb.module_home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.adapter.SeckillGoodsAdapter

class SeckillGoodsEntity(
    goods: GoodsEntity
) :
    GoodsEntity(
        goods.goodsID,
        goods.goodsName,
        goods.goodsModel,
        goods.price,
        goods.oldprice,
        goods.saleDiscount,
        goods.picURL
    ),
    MultiItemEntity {
    override fun getItemType(): Int {
        return SeckillGoodsAdapter.NORMAL
    }
}