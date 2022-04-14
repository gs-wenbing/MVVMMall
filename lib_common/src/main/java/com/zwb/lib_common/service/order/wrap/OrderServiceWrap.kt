package com.zwb.lib_common.service.order.wrap

import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.interceptor.LoginNaviCallbackImpl
import com.zwb.lib_common.service.order.IOrderService

class OrderServiceWrap private constructor() {

    @Autowired(name = RoutePath.Order.SERVICE_ORDER)
    lateinit var service: IOrderService

    init {
        ARouter.getInstance().inject(this)
    }

    fun startOrderList(activity: FragmentActivity, orderStatus: Int){
        ARouter.getInstance()
            .build(RoutePath.Order.PAGE_ORDER_LIST)
            .withInt(Constants.Order.PARAMS_ORDER_STATUS, orderStatus)
            .navigation(activity, LoginNaviCallbackImpl())
    }

    fun startOrderDetail(activity: FragmentActivity, id: Long){
        ARouter.getInstance()
            .build(RoutePath.Order.PAGE_ORDER_DETAIL)
            .withLong(Constants.Order.PARAMS_ORDER_ID, id)
            .navigation(activity, LoginNaviCallbackImpl())
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = OrderServiceWrap()
        }
    }

}