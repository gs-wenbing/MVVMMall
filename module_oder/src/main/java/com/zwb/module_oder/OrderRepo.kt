package com.zwb.module_oder

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State

class OrderRepo(private val loadState: MutableLiveData<State>):BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(OrderApi::class.java, OrderApi.BASE_URL)
    }
//    suspend fun loadSeckillGoodsCo(): List<GoodsEntity> {
//        return apiService.getSeckillGoodsList().dataConvert(loadState)
//    }
}