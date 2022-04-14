package com.zwb.lib_common.bean

class StringEvent(var event: String) {

    object Event{
        const val SWITCH_HOME = "switch_home"
        const val SWITCH_CART = "switch_cart"
        const val SWITCH_ME = "switch_me"
    }
}