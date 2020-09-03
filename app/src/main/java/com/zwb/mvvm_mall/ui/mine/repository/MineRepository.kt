package com.zwb.mvvm_mall.ui.mine.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.vm.UnPeekLiveData
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.network.dataConvert

class MineRepository (private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun loadSeckillGoodsCo(key:String): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState,key)
    }
}