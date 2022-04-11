package com.zwb.lib_common.service.goods.wrap

import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.interceptor.LoginNaviCallbackImpl
import com.zwb.lib_common.service.goods.IGoodsService

class GoodsServiceWrap private constructor() {

    @Autowired(name = RoutePath.Goods.SERVICE_GOODS)
    lateinit var service:IGoodsService

    init {
        ARouter.getInstance().inject(this)
    }

    fun startGoodsList(activity: FragmentActivity, searchKey:String){
        ARouter.getInstance()
            .build(RoutePath.Goods.PAGE_GOODS_LIST)
            .withString(RoutePath.Goods.PARAMS_SEARCH_KEY,searchKey)
            .navigation(activity,LoginNaviCallbackImpl())
    }

    fun startGoodsDetail(activity: FragmentActivity, goodsName: String){
        ARouter.getInstance()
            .build(RoutePath.Goods.PAGE_GOODS_DETAIL)
            .withString(RoutePath.Goods.PARAMS_GOODS_NAME,goodsName)
            .navigation(activity,LoginNaviCallbackImpl())
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = GoodsServiceWrap()
        }
    }
}