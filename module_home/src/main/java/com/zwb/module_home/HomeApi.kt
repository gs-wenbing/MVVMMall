package com.zwb.module_home

import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.bean.BannerEntity
import com.zwb.module_home.bean.ClassifyEntity
import retrofit2.http.GET

interface HomeApi{


    @GET(BANNER_URL)
    suspend fun loadBannerCo(): BaseResponse<List<BannerEntity>>


    @GET(SECKILL_URL)
    suspend fun getSeckillGoodsList() : BaseResponse<List<GoodsEntity>>

    /**
     * 精品列表
     */
    @GET(BOUTIQUE_URL)
    suspend fun getBoutiqueGoodsList() : BaseResponse<List<GoodsEntity>>



    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        private const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
        const val BANNER_URL = "$PROJECT/banner/json"
        const val SECKILL_URL = "$PROJECT/home/getSeckillGoodsList"
        const val BOUTIQUE_URL = "$PROJECT/home/getBoutiqueGoodsList"
    }

}