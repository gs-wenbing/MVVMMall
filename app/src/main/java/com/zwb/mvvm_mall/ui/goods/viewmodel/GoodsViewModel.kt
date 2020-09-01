package com.zwb.mvvm_mall.ui.goods.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.*
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.ui.goods.repository.GoodsListRepository

class GoodsViewModel :BaseViewModel<GoodsListRepository>(){

    var mSearchStatus:MutableLiveData<Int> = MutableLiveData()
    var mSearchKey:MutableLiveData<String> = MutableLiveData()


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

    var mCommentList: MutableLiveData<List<CommentEntity>> = MutableLiveData()
    fun loadCommentList() {
        initiateRequest({
            mCommentList.value = mRepository.loadCommentList()
        }, loadState)
    }

    var mSeckillGoods:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadSeckillGoodsData(){
        initiateRequest({
            mSeckillGoods.value = mRepository.loadGoodsList()
        }, loadState)
    }

    var mFilterAttrs:MutableLiveData<List<GoodsAttrFilterEntity>> = MutableLiveData()
    fun loadFilterAttrsData(){
        initiateRequest({
            mFilterAttrs.value = mRepository.getFilterAttrs()
        }, loadState)
    }
}