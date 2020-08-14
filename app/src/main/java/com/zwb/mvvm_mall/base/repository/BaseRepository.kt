package com.zwb.mvvm_mall.base.repository

import com.zwb.mvvm_mall.network.ApiService
import com.zwb.mvvm_mall.network.RetrofitFactory

open class BaseRepository {

    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}