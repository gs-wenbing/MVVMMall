package com.zwb.module_classify

import com.zwb.lib_base.net.BaseResponse
import com.zwb.module_classify.bean.ClassifyEntity
import retrofit2.http.GET

interface ClassifyApi {
    @GET("$PROJECT/home/getGoodsClass")
    suspend fun getGoodsClassCo() : BaseResponse<List<ClassifyEntity>>

    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
    }
}