package com.zwb.lib_common.interceptor

import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.constant.RoutePath


class LoginNaviCallbackImpl: NavigationCallback {

    override fun onFound(postcard: Postcard) {
        LogUtils.e("LoginNaviCallbackImpl","找到了")
    }

    override fun onLost(postcard: Postcard) {
        LogUtils.e("LoginNaviCallbackImpl","找不到了")
    }

    override fun onArrival(postcard: Postcard) {
        LogUtils.e("LoginNaviCallbackImpl","跳转成功了")
    }

    override fun onInterrupt(postcard: Postcard) {
        val path = postcard.path
        val bundle = postcard.extras
        ARouter.getInstance()
            .build(RoutePath.Login.PAGE_LOGIN)
            .with(bundle)
            .withString(RoutePath.PATH, path)
            .navigation();
    }
}