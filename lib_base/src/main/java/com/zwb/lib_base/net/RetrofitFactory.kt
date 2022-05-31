package com.zwb.lib_base.net
import com.zwb.lib_base.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


/**
 * Created with Android Studio.
 * Description:
 * @date: 2020/02/24
 * Time: 16:56
 */

class RetrofitFactory private constructor() {

    companion object {
        val instance by lazy {
            RetrofitFactory()
        }
    }

    fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(initOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
    private fun initOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(initLoggingIntercept())
            .addInterceptor(initCookieIntercept())
            .addInterceptor(initLoginIntercept())
            .addInterceptor(initCommonInterceptor())
            .build()
    }
    private fun initLoggingIntercept(): Interceptor {
        return HttpLoggingInterceptor { message ->
            try {
                val text: String = replacer(message)
                LogUtils.e("OKHttp-----", text)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                LogUtils.e("OKHttp-----", message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    fun replacer(outBuffer: String): String {
        var data = outBuffer
        try {
            data = data.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")
            data = data.replace("\\+".toRegex(), "%2B")
            data = URLDecoder.decode(data, "utf-8")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }
    private fun initCookieIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            LogUtils.e("OKHttp-----response", "initCookieIntercept")
            response
        }
    }

    private fun initLoginIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val response = chain.proceed(builder.build())
            LogUtils.e("OKHttp-----response", "initLoginIntercept")




            response
        }
    }

    private fun initCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "UTF-8")
                .build()
            LogUtils.e("OKHttp-----response", "initCommonInterceptor")
            val response = chain.proceed(request)
            response
        }
    }

}