package com.zwb.mvvm_mall.ui.goods.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.bean.CommentEntity
import com.zwb.mvvm_mall.bean.GoodsAttrFilterEntity
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.dataConvert

class GoodsListRepository (private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun loadBannerCo(): List<BannerResponse> {
        return apiService.loadBannerCo().dataConvert(loadState)
    }

    suspend fun loadCommentList(): List<CommentEntity> {
        return apiService.getCommentList().dataConvert(loadState)
    }

    suspend fun loadGoodsList(): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState)
    }

    suspend fun getFilterAttrs(): List<GoodsAttrFilterEntity> {
        return apiService.getFilterAttrs().dataConvert(loadState)
    }
}