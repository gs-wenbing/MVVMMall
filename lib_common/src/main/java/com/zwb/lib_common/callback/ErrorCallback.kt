package com.zwb.lib_common.callback

import com.kingja.loadsir.callback.Callback
import com.zwb.lib_common.R

class ErrorCallback :Callback(){
    override fun onCreateView(): Int = R.layout.layout_error
}