package com.zwb.module_oder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_oder.bean.OrderEntity
import com.zwb.module_oder.bean.OrderGoodsEntity
import com.zwb.module_oder.bean.OrderOperateEntity
import com.zwb.module_oder.bean.OrderTitleEntity

class OrderViewModel : BaseViewModel() {
    private val repository by lazy {
        OrderRepo(loadState)
    }

    fun loadOrderList(pageSize: Int, page: Int, ordersStatus: Int): MutableLiveData<List<MultiItemEntity>> {
        val orderLiveData: MutableLiveData<List<MultiItemEntity>> = MutableLiveData()
        initiateRequest({
            val list = mutableListOf<MultiItemEntity>()
            if (page >= 3) {
                orderLiveData.value = list
            } else {
                val orderList = repository.loadOrderList(pageSize, page, ordersStatus, OrderApi.ORDER_LIST_URL)
                orderList.forEach { order ->
                    list.add(OrderTitleEntity(order.shopID, order.shopName, order.shopIcon, order.orderStatus))
                    list.addAll(order.goodsList)
                    list.add(OrderOperateEntity(order.orderStatus, order.goodsList.sumByDouble { it.price * it.num }))
                }
                orderLiveData.value = list
            }
        }, loadState , OrderApi.ORDER_LIST_URL)
        return orderLiveData
    }

}