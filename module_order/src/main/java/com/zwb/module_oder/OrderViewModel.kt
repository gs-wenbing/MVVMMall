package com.zwb.module_oder

import com.zwb.lib_base.mvvm.vm.BaseViewModel

class OrderViewModel:BaseViewModel() {
    private val repository by lazy {
        OrderRepo(loadState)
    }

//    var mSeckillGoods: MutableLiveData<List<GoodsEntity>> = MutableLiveData()
//    fun loadSeckillGoodsData(){
//        initiateRequest({
//            mSeckillGoods.value = repository.loadSeckillGoodsCo()
//        }, loadState)
//    }
}