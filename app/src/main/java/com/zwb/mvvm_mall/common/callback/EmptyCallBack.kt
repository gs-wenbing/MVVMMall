package com.zwb.mvvm_mall.common.callback

import com.kingja.loadsir.callback.Callback
import com.zwb.mvvm_mall.R

class EmptyCallBack :Callback(){
    override fun onCreateView(): Int = R.layout.layout_empty
}