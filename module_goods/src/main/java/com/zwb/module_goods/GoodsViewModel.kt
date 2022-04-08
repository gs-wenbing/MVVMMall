package com.zwb.module_goods

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_goods.bean.CommentEntity
import com.zwb.module_goods.bean.GoodsAttrFilterEntity
import com.zwb.module_goods.bean.SearchHotEntity
import com.zwb.module_goods.bean.SearchTagEntity

class GoodsViewModel:BaseViewModel() {
    private val repository by lazy { GoodsRepo(loadState)}

    var mSearchStatus: MutableLiveData<Int> = MutableLiveData()
    var mSearchKey:MutableLiveData<String> = MutableLiveData()

    var mSearchTagsData:MutableLiveData<List<SearchTagEntity>> = MutableLiveData()

    fun loadSearchTags() {
        initiateRequest({
            mSearchTagsData.value = repository.getSearchTags()
        }, loadState)
    }

    var mSearchHotData:MutableLiveData<List<SearchHotEntity>> = MutableLiveData()
    fun loadSearchHotData() {
        initiateRequest({
            mSearchHotData.value = repository.getSearchHotTags()
        }, loadState)
    }

    var mCommentList: MutableLiveData<List<CommentEntity>> = MutableLiveData()
    fun loadCommentList() {
        initiateRequest({
            mCommentList.value = repository.loadCommentList()
        }, loadState)
    }

    var mSeckillGoods:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadSeckillGoodsData(){
        initiateRequest({
            mSeckillGoods.value = repository.loadGoodsList()
        }, loadState)
    }

    var mFilterAttrs:MutableLiveData<List<GoodsAttrFilterEntity>> = MutableLiveData()
    fun loadFilterAttrsData(){
        initiateRequest({
            mFilterAttrs.value = repository.getFilterAttrs()
        }, loadState)
    }
}