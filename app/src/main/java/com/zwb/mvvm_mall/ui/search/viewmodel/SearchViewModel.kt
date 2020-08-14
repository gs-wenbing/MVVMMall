package com.zwb.mvvm_mall.ui.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.SearchHotEntity
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.bean.SearchTagEntity
import com.zwb.mvvm_mall.ui.search.repository.SearchRepository

class SearchViewModel: BaseViewModel<SearchRepository>(){
    var mSearchTagsData:MutableLiveData<List<SearchTagEntity>> = MutableLiveData()

    fun loadSearchTags() {
        initiateRequest({
            mSearchTagsData.value = mRepository.getSearchTags()
        }, loadState)
    }

    var mSearchHotData:MutableLiveData<List<SearchHotEntity>> = MutableLiveData()
    fun loadSearchHotData() {
        initiateRequest({
            mSearchHotData.value = mRepository.getSearchHotTags()
        }, loadState)
    }

}