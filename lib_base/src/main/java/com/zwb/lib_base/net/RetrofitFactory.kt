package com.zwb.lib_base.net
import android.util.Log
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
                val text: String = URLDecoder.decode(message, "utf-8")
                Log.e("OKHttp-----", text)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                Log.e("OKHttp-----", message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun initCookieIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response
        }
    }

    private fun initLoginIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val response = chain.proceed(builder.build())
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
            chain.proceed(request)
        }
    }

    private fun parseCookie(it: List<String>): String {
        if(it.isEmpty()){
            return ""
        }

        val stringBuilder = StringBuilder()

        it.forEach { cookie ->
            stringBuilder.append(cookie).append(";")
        }

        if(stringBuilder.isEmpty()){
            return ""
        }
        //末尾的";"去掉
        return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
    }

    private fun saveCookie(domain: String?, parseCookie: String) {
//        domain?.let {
//            var resutl :String by SPreference("cookie", parseCookie)
//            resutl = parseCookie
//        }
    }
}