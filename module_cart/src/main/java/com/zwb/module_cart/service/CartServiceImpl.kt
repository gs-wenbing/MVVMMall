package com.zwb.module_cart.service

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.interceptor.LoginInterceptor
import com.zwb.lib_common.service.cart.ICartService
import com.zwb.module_cart.fragment.CartFragment

@Route(path = RoutePath.Cart.SERVICE_CART)
class CartServiceImpl: ICartService {

    override fun getFragment(): Fragment {
        val isLogin = SpUtils.getBoolean("login", false)
        if(isLogin == true){
            Log.e(LoginInterceptor.TAG,"已经登录了")
        }else{
            Log.e(LoginInterceptor.TAG,"未登录")
        }
        return CartFragment()
    }

    override fun init(context: Context?) {

    }
}