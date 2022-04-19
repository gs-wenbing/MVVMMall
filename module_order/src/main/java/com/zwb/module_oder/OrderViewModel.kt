package com.zwb.module_oder

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.module_oder.bean.OrderEntity
import com.zwb.module_oder.bean.OrderOperateEntity
import com.zwb.module_oder.bean.OrderTitleEntity

class OrderViewModel : BaseViewModel() {
    private val repository by lazy {
        OrderRepo(loadState)
    }
    fun loadOrderList(pageSize: Int, page: Int): MutableLiveData<List<MultiItemEntity>> {
        val orderLiveData: MutableLiveData<List<MultiItemEntity>> = MutableLiveData()
        initiateRequest({
            val list = mutableListOf<MultiItemEntity>()
            if(page!=3){
                for (i in 0..pageSize) {
                    list.add(OrderTitleEntity(status = 1, shopName = "南极人京梦专卖店"))
                    list.add(OrderEntity(status = 1, picURL = "https://img10.360buyimg.com/n7/jfs/t1/124265/28/7893/157540/5f1990d6Ef8788171/f2c3025a5c1f0854.jpg", price = 66.88))
                    list.add(OrderEntity(status = 1, picURL = "https://img12.360buyimg.com/n7/jfs/t1/133577/22/5429/210734/5f1d74d9E95167cc0/88c7d5ba36862fbd.jpg", price = 780.45))
                    list.add(OrderOperateEntity(status = 1))

                    list.add(OrderTitleEntity(status = 1, shopName = "零趣食品旗舰店"))
                    list.add(OrderEntity(status = 1, picURL = "https://img10.360buyimg.com/n7/jfs/t1/122036/22/4363/200013/5edde4f3Eb64828db/c2928a893eb7be5d.jpg", price = 60.00))
                    list.add(OrderEntity(status = 1, picURL = "https://img14.360buyimg.com/n7/jfs/t1/129185/33/1152/159254/5eba20deE46e394c3/a532e511742e0100.jpg", price = 23.45))
                    list.add(OrderEntity(status = 1, picURL = "https://img12.360buyimg.com/n7/jfs/t1/146921/34/3860/224514/5f1d74daE90c0a291/5343444e23e60f92.jpg", price = 888.99))
                    list.add(OrderOperateEntity(status = 1))
                }
            }
            orderLiveData.value = list
        }, loadState)
        return orderLiveData
    }


}