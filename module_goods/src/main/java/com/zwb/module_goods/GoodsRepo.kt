package com.zwb.module_goods

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_goods.bean.CommentEntity
import com.zwb.module_goods.bean.GoodsAttrFilterEntity
import com.zwb.module_goods.bean.SearchHotEntity
import com.zwb.module_goods.bean.SearchTagEntity

class GoodsRepo(private val loadState: MutableLiveData<State>) : BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(GoodsApi::class.java, GoodsApi.BASE_URL)
    }

    suspend fun getSearchTags(): List<SearchTagEntity> {
        return apiService.getSearchTags().dataConvert(loadState)
    }

    suspend fun getSearchHotTags(): List<SearchHotEntity> {
        return apiService.getSearchHotTags().dataConvert(loadState)
    }

    suspend fun loadCommentList(): List<CommentEntity> {
        return apiService.getCommentList().dataConvert(loadState)
    }

    suspend fun loadGoodsList(loadKey: String): List<GoodsEntity> {
        return apiService.getSeckillGoodsList().dataConvert(loadState, loadKey)
    }

    suspend fun getFilterAttrs(): List<GoodsAttrFilterEntity> {
        return apiService.getFilterAttrs().dataConvert(loadState)
    }
}