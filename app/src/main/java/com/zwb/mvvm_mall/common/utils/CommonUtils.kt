package com.zwb.mvvm_mall.common.utils

import android.content.Context
import android.widget.Toast
import java.lang.reflect.ParameterizedType

object CommonUtils{

    fun <T> getClass(t:Any):Class<T>{
        return (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<T>
    }
}