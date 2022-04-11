package com.zwb.module_goods.service

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.goods.IGoodsService
import com.zwb.module_goods.activity.GoodsDetailActivity
import com.zwb.module_goods.activity.GoodsListActivity

@Route(path = RoutePath.Goods.SERVICE_GOODS)
class GoodsServiceImpl: IGoodsService {

    override fun init(context: Context?) {
        Log.e("GoodsServiceImpl","GoodsServiceImpl")
    }

    override fun startGoodsList(activity: FragmentActivity, searchKey: String) {
        GoodsListActivity.launch(activity,searchKey)
    }

    override fun startGoodsDetail(activity: FragmentActivity, goodsName: String) {
        GoodsDetailActivity.launch(activity,goodsName)
    }

}