package com.zwb.module_goods.service

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.goods.IGoodsService

@Route(path = RoutePath.Goods.SERVICE_GOODS)
class GoodsServiceImpl: IGoodsService {
    override fun test() {

    }

    override fun init(context: Context?) {
        Log.e("GoodsServiceImpl","GoodsServiceImpl")
    }



}