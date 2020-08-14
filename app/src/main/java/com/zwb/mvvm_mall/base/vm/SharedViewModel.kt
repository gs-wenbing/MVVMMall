package com.zwb.mvvm_mall.base.vm

import androidx.lifecycle.ViewModel

class SharedViewModel :ViewModel(){

    val windowFocusChangedListener: UnPeekLiveData<Boolean> = UnPeekLiveData()
}