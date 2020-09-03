package com.zwb.mvvm_mall.ui.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.ui.mine.repository.MineRepository

class MineViewModel: BaseViewModel<MineRepository>(){


    var mSeckillGoods:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadSeckillGoodsData(key:String){
        initiateRequest({
            mSeckillGoods.value = mRepository.loadSeckillGoodsCo(key)
        }, loadState)
    }



}