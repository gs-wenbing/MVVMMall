package com.zwb.module_me

import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.GoodsEntity
import retrofit2.http.GET

interface MeApi{



    @GET("$PROJECT/home/getSeckillGoodsList")
    suspend fun getSeckillGoodsList() : BaseResponse<List<GoodsEntity>>


    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
    }

}