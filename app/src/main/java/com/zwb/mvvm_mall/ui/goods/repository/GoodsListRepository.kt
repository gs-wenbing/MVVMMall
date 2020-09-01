package com.zwb.mvvm_mall.ui.goods.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.bean.*
import com.zwb.mvvm_mall.network.dataConvert

class GoodsListRepository (private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun getSearchTags(): List<SearchTagEntity> {
        return apiService.getSearchTags().dataConvert(loadState)
    }
    suspend fun getSearchHotTags(): List<SearchHotEntity> {
        return apiService.getSearchHotTags().dataConvert(loadState)
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