package com.zwb.lib_common.interceptor

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.constant.SpKey

@Interceptor(name = "login", priority = 1)
class LoginInterceptor: IInterceptor {

    override fun init(context: Context?) {
        LogUtils.e(TAG,"路由登录拦截器初始化成功")
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val path = postcard.path
        val isLogin = SpUtils.getBoolean(SpKey.IS_LOGIN, false)
        if(isLogin == true){
            LogUtils.e(TAG,"已经登录了")
            callback.onContinue(postcard);
        }else{
            when (path) {
                RoutePath.Login.PAGE_LOGIN,
                RoutePath.Goods.PAGE_GOODS_LIST,
                RoutePath.Goods.PAGE_GOODS_DETAIL -> {
                    LogUtils.e(TAG,"不需要登录")
                    callback.onContinue(postcard);
                }
                else -> {
                    LogUtils.e(TAG,"未登录")
                    callback.onInterrupt(null);
                }
            }
        }
    }

    companion object {
        const val TAG = "LoginInterceptor"
    }
}