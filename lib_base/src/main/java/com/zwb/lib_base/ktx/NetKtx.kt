package com.zwb.lib_base.ktx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.NetExceptionHandle
import com.zwb.lib_base.net.State
import com.zwb.lib_base.net.StateType
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:数据解析扩展函数
 * @CreateDate: 2020/4/19 17:35
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>,
    urlKey: String = ""
): T {
    return when (errorCode) {
        0 -> {
            if (data is List<*>) {
                if ((data as List<*>).isEmpty()) {
                    loadState.value = State(StateType.EMPTY, urlKey)
                } else {
                    loadState.value = State(StateType.SUCCESS, urlKey)
                }
            } else {
                loadState.value = State(StateType.SUCCESS, urlKey)
            }
            data
        }
        else -> {
            loadState.postValue(State(StateType.ERROR, urlKey, message = errorMsg))
            data
        }
    }
}


fun BaseViewModel.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>,
    urlKey: String = ""
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState, urlKey)
        }
    }
}
