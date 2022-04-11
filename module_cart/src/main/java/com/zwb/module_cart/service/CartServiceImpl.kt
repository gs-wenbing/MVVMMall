package com.zwb.module_cart.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.cart.ICartService
import com.zwb.module_cart.fragment.CartFragment

@Route(path = RoutePath.Cart.SERVICE_CART)
class CartServiceImpl:ICartService {
    override fun getFragment(): Fragment {
        return CartFragment()
    }

    override fun init(context: Context?) {

    }
}