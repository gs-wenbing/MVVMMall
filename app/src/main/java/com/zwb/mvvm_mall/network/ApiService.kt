package com.zwb.mvvm_mall.network

import com.zwb.mvvm_mall.bean.*
import com.zwb.mvvm_mall.common.utils.Constant.PROJECT
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService{

    @GET("/banner/json")
    fun loadBanner(): Observable<BaseResponse<List<BannerResponse>>>
    @GET("$PROJECT/banner/json")
    suspend fun loadBannerCo(): BaseResponse<List<BannerResponse>>

    @GET("$PROJECT/home/getGoodsClass")
    suspend fun getGoodsClassCo() : BaseResponse<List<ClassifyEntity>>

    @GET("$PROJECT/home/getSeckillGoodsList")
    suspend fun getSeckillGoodsList() : BaseResponse<List<GoodsEntity>>

    /**
     * 精品列表
     */
    @GET("$PROJECT/home/getBoutiqueGoodsList")
    suspend fun getBoutiqueGoodsList() : BaseResponse<List<GoodsEntity>>

    @GET("$PROJECT/home/getCartList")
    suspend fun getCartList() : BaseResponse<List<CartGoodsEntity>>

    @GET("$PROJECT/home/getSeckillGoodsList")
    suspend fun getCartLikeGoods() : BaseResponse<List<CartLikeGoodsEntity>>

    @GET("$PROJECT/search/getSearchTags")
    suspend fun getSearchTags() : BaseResponse<List<SearchTagEntity>>

    @GET("$PROJECT/search/getSearchHotTags")
    suspend fun getSearchHotTags() : BaseResponse<List<SearchHotEntity>>

    @GET("$PROJECT/comment/getCommentList")
    suspend fun getCommentList() : BaseResponse<List<CommentEntity>>

    @GET("$PROJECT/goods/getFilterAttrs")
    suspend fun getFilterAttrs() : BaseResponse<List<GoodsAttrFilterEntity>>



}