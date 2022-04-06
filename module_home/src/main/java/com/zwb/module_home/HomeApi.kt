package com.zwb.module_home

import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.bean.BannerEntity
import com.zwb.module_home.bean.ClassifyEntity
import retrofit2.http.GET

interface HomeApi{


    @GET("$PROJECT/banner/json")
    suspend fun loadBannerCo(): BaseResponse<List<BannerEntity>>

    @GET("$PROJECT/home/getGoodsClass")
    suspend fun getGoodsClassCo() : BaseResponse<List<ClassifyEntity>>

    @GET("$PROJECT/home/getSeckillGoodsList")
    suspend fun getSeckillGoodsList() : BaseResponse<List<GoodsEntity>>

    /**
     * 精品列表
     */
    @GET("$PROJECT/home/getBoutiqueGoodsList")
    suspend fun getBoutiqueGoodsList() : BaseResponse<List<GoodsEntity>>



    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
    }

}