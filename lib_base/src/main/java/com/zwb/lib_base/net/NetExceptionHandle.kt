package com.zwb.lib_base.net

import android.net.ParseException
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created with Android Studio.
 * Description:
 * @CreateDate: 2020/5/5 11:32
 */
object NetExceptionHandle {
    fun handleException(e: Throwable?, loadState: MutableLiveData<State>,  urlKey:String){
        e?.let {
            when (it) {
                is HttpException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message=it.message()))
                }
                is ConnectException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="网络连接异常，请检查网络"))
                }
                is ConnectTimeoutException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="网络连接超时，请稍后重试"))
                }
                is SocketTimeoutException ->{
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="网络连接超时，请稍后重试"))
                }
                is UnknownHostException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="网络异常，请检查网络"))
                }
                is JSONException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="数据解析失败"))
                }
                is ParseException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="数据解析失败"))
                }
                is JsonParseException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="数据解析失败"))
                }
                is IOException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message="网络连接异常!"))
                }
                else -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,urlKey,message=(it.message.toString())))
                }
            }
        }
    }
}