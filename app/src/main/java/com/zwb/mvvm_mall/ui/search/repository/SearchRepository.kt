package com.zwb.mvvm_mall.ui.search.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.network.dataConvert
import com.zwb.mvvm_mall.bean.SearchHotEntity
import com.zwb.mvvm_mall.bean.SearchTagEntity

class SearchRepository (private val loadState: MutableLiveData<State>): BaseRepository() {


    suspend fun getSearchTags(): List<SearchTagEntity> {
        return apiService.getSearchTags().dataConvert(loadState)
    }
    suspend fun getSearchHotTags(): List<SearchHotEntity> {
        return apiService.getSearchHotTags().dataConvert(loadState)
    }
}