package com.zwb.lib_base.net
import androidx.annotation.StringRes

data class State(var code: StateType, var urlKey:String="", var message:String="", @StringRes var tip:Int=0)