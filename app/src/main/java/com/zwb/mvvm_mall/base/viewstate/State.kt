package com.zwb.mvvm_mall.base.viewstate

import androidx.annotation.StringRes

data class State(var code: StateType,var urlKey:String="", var message:String="", @StringRes var tip:Int=0)