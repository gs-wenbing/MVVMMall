package com.zwb.mvvm_mall.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zwb.mvvm_mall.common.utils.Constant
import com.wjx.android.wanandroidmvvm.network.NetExceptionHandle
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:数据解析扩展函数
 * @author: Wangjianxian
 * @CreateDate: 2020/4/19 17:35
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>
): T {
    when (errorCode) {
        Constant.SUCCESS -> {
            if (data is List<*>) {
                if ((data as List<*>).isEmpty()) {
                    loadState.postValue(State(StateType.EMPTY))
                }
            }
            loadState.postValue(State(StateType.SUCCESS))
            return data
        }
//        Constant.NOT_LOGIN -> {
//            UserInfo.instance.logoutSuccess()
//            loadState.postValue(State(StateType.ERROR, message = "请重新登录"))
//            return data
//        }
        else -> {
            loadState.postValue(State(StateType.ERROR, message = errorMsg))
            return data
        }
    }
}


fun <T : BaseRepository> BaseViewModel<T>.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}
