package com.zwb.module_cart.service

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.constant.SpKey
import com.zwb.lib_common.interceptor.LoginInterceptor
import com.zwb.lib_common.service.cart.ICartService
import com.zwb.module_cart.fragment.CartFragment

@Route(path = RoutePath.Cart.SERVICE_CART)
class CartServiceImpl: ICartService {

    override fun getFragment(): Fragment {
        return CartFragment()
    }

    override fun init(context: Context?) {

    }
}