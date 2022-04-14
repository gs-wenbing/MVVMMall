package com.zwb.module_oder.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.order.IOrderService

@Route(path = RoutePath.Order.SERVICE_ORDER)
class OrderServiceImpl: IOrderService {

    override fun init(context: Context?) {

    }
}