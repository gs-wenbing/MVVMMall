package com.zwb.module_home

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.bean.BannerEntity
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val loadState: MutableLiveData<State>):BaseRepository() {

    private val apiService by lazy {
        RetrofitFactory.instance.getService(HomeApi::class.java, HomeApi.BASE_URL)
    }

    suspend fun loadBannerCo(key: String): List<BannerEntity> {
        return apiService.loadBannerCo().dataConvert(loadState, key)
    }

    suspend fun loadSeckillGoodsCo(key: String): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState, key)
    }

    suspend fun loadBoutiqueGoodsCo(key: String): List<GoodsEntity> {
        return apiService.getBoutiqueGoodsList().dataConvert(loadState, key)
    }
}