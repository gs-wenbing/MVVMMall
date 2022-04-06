package com.zwb.module_cart

import com.zwb.lib_base.net.BaseResponse
import com.zwb.module_cart.baen.CartGoodsEntity
import com.zwb.module_cart.baen.CartLikeGoodsEntity
import retrofit2.http.GET

interface CartApi{


    @GET("$PROJECT/home/getCartList")
    suspend fun getCartList() : BaseResponse<List<CartGoodsEntity>>

    @GET("$PROJECT/home/getSeckillGoodsList")
    suspend fun getCartLikeGoods() : BaseResponse<List<CartLikeGoodsEntity>>

    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
    }

}