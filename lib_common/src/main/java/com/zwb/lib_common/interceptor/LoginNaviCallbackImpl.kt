package com.zwb.lib_common.interceptor

import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback

class LoginNaviCallbackImpl: NavigationCallback {

    override fun onFound(postcard: Postcard) {

    }

    override fun onLost(postcard: Postcard) {
    }

    override fun onArrival(postcard: Postcard) {
    }

    override fun onInterrupt(postcard: Postcard) {
        val path = postcard.path
        Log.e("LoginInterceptor",path)
    }
}