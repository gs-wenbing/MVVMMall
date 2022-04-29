package com.zwb.lib_base.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

class GsonUtils {
    private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    private val sLocalGson = createLocalGson()

    private val sRemoteGson = createRemoteGson()

    private fun createLocalGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gsonBuilder.setDateFormat(DATE_FORMAT)
        return gsonBuilder
    }

    private fun createLocalGson(): Gson {
        return createLocalGsonBuilder().create()
    }

    private fun createRemoteGson(): Gson {
        return createLocalGsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    fun getLocalGson(): Gson? {
        return sLocalGson
    }

    @Throws(JsonSyntaxException::class)
    fun <T> fromLocalJson(json: String?, clazz: Class<T>?): T? {
        return try {
            sLocalGson.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    fun <T> fromLocalJson(json: String?, typeOfT: Type?): T {
        return sLocalGson.fromJson(json, typeOfT)
    }

    fun toJson(src: Any?): String? {
        return sLocalGson.toJson(src)
    }

    fun toRemoteJson(src: Any?): String? {
        return sRemoteGson.toJson(src)
    }
}