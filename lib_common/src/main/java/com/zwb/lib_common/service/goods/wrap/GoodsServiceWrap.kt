package com.zwb.lib_common.service.goods.wrap

import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.goods.IGoodsService

class GoodsServiceWrap private constructor() {

    @Autowired(name = RoutePath.Goods.SERVICE_GOODS)
    lateinit var service:IGoodsService

    init {
        ARouter.getInstance().inject(this)
    }

    fun startGoodsList(activity: FragmentActivity, searchKey:String){
        service.startGoodsList(activity,searchKey)
    }

    fun startGoodsDetail(activity: FragmentActivity, goodsName: String){
        service.startGoodsDetail(activity,goodsName)
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = GoodsServiceWrap()
        }
    }
}