package com.zwb.module_my

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.GoodsEntity

class MyRepo(private val loadState: MutableLiveData<State>):BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(MyApi::class.java, MyApi.BASE_URL)
    }
    suspend fun loadSeckillGoodsCo(): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState)
    }
}