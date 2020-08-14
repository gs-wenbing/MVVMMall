package com.zwb.mvvm_mall.ui.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.ui.mine.repository.MineRepository

class MineViewModel: BaseViewModel<MineRepository>(){
    var mBannerData: MutableLiveData<List<BannerResponse>> = MutableLiveData()

    fun loadBannerCo() {
        initiateRequest({
            mBannerData.value = mRepository.loadBannerCo()
        }, loadState)
    }


    var mSeckillGoods:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadSeckillGoodsData(){
        initiateRequest({
            mSeckillGoods.value = mRepository.loadSeckillGoodsCo()
        }, loadState)
    }


    var mListScrollEnabled:MutableLiveData<Boolean> = MutableLiveData()

    var mListData:MutableLiveData<List<String>> = MutableLiveData()
    fun loadListData(){
        //创建一个带元素的可变集合
        val list = mutableListOf("22","33","22","33","22","33","22","33","22","33","33","22","33","22","33","22","33","33","22","33","22","33","22","33","33","22","33","22","33","22","33","22","33","22","33","22","33","33","22","33","22","33","22","33","33","22","33","22","33","22","33","33","22","33","22","33","22","33","22","33","22","33","22","33","33","22","33","22","33","22","33","33","22","33","22","33","22","33","33","22","33")
        mListData.value = list
    }
}