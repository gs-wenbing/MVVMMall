package com.zwb.mvvm_mall.base.view

import android.view.View

interface BaseView {
    fun registerDefaultLoad(view: View, key:String="")
    fun showLoading(key:String="")
    fun showSuccess(key:String="")
    fun showEmpty(key:String="")
    fun showError(msg: String,key:String="")
}