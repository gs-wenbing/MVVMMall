package com.zwb.mvvm_mall.network

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParseException
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import com.zwb.mvvm_mall.base.vm.UnPeekLiveData
import com.zwb.mvvm_mall.common.utils.Constant
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created with Android Studio.
 * Description:
 * @CreateDate: 2020/5/5 11:32
 */
object NetExceptionHandle {
    fun handleException(e: Throwable?, loadState: MutableLiveData<State>){
        e?.let {
            when (it) {
                is HttpException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,Constant.COMMON_KEY))
                }
                is ConnectException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,Constant.COMMON_KEY))
                }
                is ConnectTimeoutException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,Constant.COMMON_KEY))
                }
                is UnknownHostException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,Constant.COMMON_KEY))
                }
                is JsonParseException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,Constant.COMMON_KEY))
                }
            }
        }
    }
}