package com.zwb.lib_common.constant

object Constants {

    const val REQUEST_CODE_CART = 1
    const val REQUEST_CODE_ME = 2

    object Home{
        const val HOME = 0
        const val CLASSIFY = 1
        const val CART = 2
        const val MINE = 3
    }
    object Order{
        const val PARAMS_ORDER_STATUS = "orderStatus"
        const val PARAMS_ORDER_ID = "order_id"

        const val ORDER_ALL = 100
        const val ORDER_NOT_PAY = 1
        const val ORDER_NOT_SENT = 2
        const val ORDER_NOT_RECEIVE = 3
        const val ORDER_NOT_COMMENT = 4
        const val ORDER_CLOSE = 9
    }

    object Goods{
        const val PARAMS_SEARCH_KEY = "search_key"
        const val PARAMS_GOODS_NAME = "goods_name"
    }
}