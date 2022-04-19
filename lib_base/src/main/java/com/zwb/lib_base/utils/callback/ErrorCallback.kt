package com.zwb.lib_base.utils.callback

import com.kingja.loadsir.callback.Callback
import com.zwb.lib_base.R

class ErrorCallback :Callback(){
    override fun onCreateView(): Int = R.layout.layout_error
}