package com.zwb.lib_common.interceptor

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.RoutePath

@Interceptor(name = "login", priority = 1)
class LoginInterceptor: IInterceptor {

    override fun init(context: Context?) {
        Log.e(TAG,"路由登录拦截器初始化成功")
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val path = postcard.path
        val isLogin = SpUtils.getBoolean("login", false)
        if(isLogin == true){
            Log.e(TAG,"已经登录了")
            callback.onContinue(postcard);
        }else{
            when (path) {
                RoutePath.Goods.PAGE_GOODS_LIST,
                RoutePath.Goods.PAGE_GOODS_DETAIL -> {
                    Log.e(TAG,"不需要登录")
                    callback.onContinue(postcard);
                }
                else -> {
                    Log.e(TAG,"未登录")
                    callback.onInterrupt(null);
                }
            }
        }
    }

    companion object {
        const val TAG = "LoginInterceptor"
    }
}