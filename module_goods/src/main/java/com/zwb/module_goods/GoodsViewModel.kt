package com.zwb.module_goods

import com.zwb.lib_base.mvvm.vm.BaseViewModel

class GoodsViewModel:BaseViewModel() {
    private val repository by lazy {
        GoodsRepo(loadState)
    }

//    var mSeckillGoods: MutableLiveData<List<GoodsEntity>> = MutableLiveData()
//    fun loadSeckillGoodsData(){
//        initiateRequest({
//            mSeckillGoods.value = repository.loadSeckillGoodsCo()
//        }, loadState)
//    }
}