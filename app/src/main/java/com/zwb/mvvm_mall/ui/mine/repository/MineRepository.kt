package com.zwb.mvvm_mall.ui.mine.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.dataConvert
import com.zwb.mvvm_mall.bean.BannerResponse

class MineRepository (private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun loadBannerCo(): List<BannerResponse> {
        return apiService.loadBannerCo().dataConvert(loadState)
    }

    suspend fun loadSeckillGoodsCo(): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState)
    }
}