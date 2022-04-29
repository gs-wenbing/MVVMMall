package com.zwb.lib_common.constant

/**
 * @Author: QuYunShuo
 * @Time: 2020/8/28
 * @Class: RoutePath
 * @Remark: 路由地址
 */
object RoutePath{

    const val PATH = "path"

    object Login{
        private const val LOGIN = "/login"
        const val PAGE_LOGIN = "$LOGIN/LoginActivity"
    }

    object Home{
        private const val HOME = "/home"
        const val FRAGMENT_HOME = "$HOME/HomeFragment"
        const val SERVICE_HOME = "${HOME}/home_service"
    }

    object Classify{
        private const val CLASSIFY = "/classify"
        const val FRAGMENT_CLASSIFY = "$CLASSIFY/ClassifyFragment"
        const val SERVICE_CLASSIFY = "${CLASSIFY}/classify_service"
    }

    object Cart{
        private const val CART = "/cart"
        const val FRAGMENT_CART = "$CART/CartFragment"
        const val SERVICE_CART = "${CART}/cart_service"
    }

    object Me{
        private const val ME = "/me"
        const val FRAGMENT_ME = "$ME/MeFragment"
        const val SERVICE_ME = "${ME}/me_service"
    }

    object Goods{
        private const val GOODS = "/goods"
        const val PAGE_GOODS_LIST = "$GOODS/GoodsListActivity"
        const val PAGE_GOODS_DETAIL = "$GOODS/GoodsDetailActivity"
        const val SERVICE_GOODS = "$GOODS/goods_service"
    }

    object Order{
        private const val ORDER = "/order"
        const val PAGE_ORDER_LIST = "$ORDER/OrderListActivity"
        const val PAGE_ORDER_DETAIL = "$ORDER/OrderDetailActivity"
        const val SERVICE_ORDER = "$ORDER/order_service"
    }
    object Video{
        private const val VIDEO = "/video"
        const val FRAGMENT_VIDEO = "${VIDEO}/VideoMainFragment"
        const val SERVICE_VIDEO = "${VIDEO}/video_service"
    }
}