package com.zwb.mvvm_mall.ui.goods.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.dataConvert

class GoodsListRepository (private val loadState: MutableLiveData<State>): BaseRepository() {


    suspend fun loadGoodsList(): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState)
    }
}