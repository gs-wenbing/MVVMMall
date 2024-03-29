package com.zwb.module_oder

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.constant.Constants
import com.zwb.module_oder.bean.OrderEntity


class OrderRepo(private val loadState: MutableLiveData<State>) : BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(OrderApi::class.java, OrderApi.BASE_URL)
    }

    suspend fun loadOrderList(pageSize: Int, page: Int, status: Int, key:String): List<OrderEntity> {
        val orderList = apiService.getOrderList().dataConvert(loadState, key)
        if(status == Constants.Order.ORDER_ALL){
            return orderList
        }
        return orderList.filter { it.orderStatus == status }
    }

}