package com.zwb.mvvm_mall.base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.common.utils.CommonUtils

open class BaseViewModel<R: BaseRepository>: ViewModel() {

    val loadState by lazy {
        MutableLiveData<State>()
    }

    val mRepository by lazy {
        CommonUtils.getClass<R>(this)
            .getDeclaredConstructor(MutableLiveData::class.java)
            .newInstance(loadState)
    }

    override fun onCleared() {
        super.onCleared()
    }
}