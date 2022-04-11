package com.zwb.lib_common.service.cart.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.cart.ICartService

class CartServiceWrap {
    @Autowired(name = RoutePath.Cart.SERVICE_CART)
    lateinit var service: ICartService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = CartServiceWrap()
        }
    }
}