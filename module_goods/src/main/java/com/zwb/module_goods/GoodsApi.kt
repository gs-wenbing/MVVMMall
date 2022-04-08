package com.zwb.module_goods

import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_goods.bean.CommentEntity
import com.zwb.module_goods.bean.GoodsAttrFilterEntity
import com.zwb.module_goods.bean.SearchHotEntity
import com.zwb.module_goods.bean.SearchTagEntity
import retrofit2.http.GET

interface GoodsApi {

    @GET("$PROJECT/home/getSeckillGoodsList")
    suspend fun getSeckillGoodsList() : BaseResponse<List<GoodsEntity>>
    @GET("$PROJECT/search/getSearchTags")
    suspend fun getSearchTags() : BaseResponse<List<SearchTagEntity>>

    @GET("$PROJECT/search/getSearchHotTags")
    suspend fun getSearchHotTags() : BaseResponse<List<SearchHotEntity>>

    @GET("$PROJECT/comment/getCommentList")
    suspend fun getCommentList() : BaseResponse<List<CommentEntity>>

    @GET("$PROJECT/goods/getFilterAttrs")
    suspend fun getFilterAttrs() : BaseResponse<List<GoodsAttrFilterEntity>>

    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
    }
}