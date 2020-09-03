package com.zwb.mvvm_mall.ui.home.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.vm.UnPeekLiveData
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.dataConvert

class HomeRepository(private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun loadBannerCo(): List<BannerResponse> {
        return apiService.loadBannerCo().dataConvert(loadState)
    }

    suspend fun loadSeckillGoodsCo(): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState)
    }

    suspend fun loadBoutiqueGoodsCo(): List<GoodsEntity> {
        return apiService.getBoutiqueGoodsList().dataConvert(loadState)
    }

}