package com.zwb.module_oder

import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_oder.bean.OrderEntity
import retrofit2.http.GET

interface OrderApi {

    @GET(ORDER_LIST_URL)
    suspend fun getOrderList() : BaseResponse<List<OrderEntity>>


    companion object {
        const val BASE_URL = "https://mockapi.eolink.com/"
        const val PROJECT = "/JiPqtefd9325e3466dc720a8c0ad3364c4f791e227debcd"
        const val ORDER_LIST_URL = "$PROJECT/order/getOrderList"
    }
}