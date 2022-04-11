package com.zwb.module_my

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_common.bean.GoodsEntity

class MeViewModel:BaseViewModel() {
    private val repository by lazy {
        MeRepo(loadState)
    }

    var mSeckillGoods: MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadSeckillGoodsData(){
        initiateRequest({
            mSeckillGoods.value = repository.loadSeckillGoodsCo()
        }, loadState)
    }
}