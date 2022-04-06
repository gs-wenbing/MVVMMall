package com.zwb.module_goods

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State

class GoodsRepo(private val loadState: MutableLiveData<State>):BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(GoodsApi::class.java, GoodsApi.BASE_URL)
    }
//    suspend fun loadSeckillGoodsCo(): List<GoodsEntity> {
//        return apiService.getSeckillGoodsList().dataConvert(loadState)
//    }
}