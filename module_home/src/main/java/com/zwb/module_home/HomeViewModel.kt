package com.zwb.module_home

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.bean.BannerEntity

class HomeViewModel:BaseViewModel() {

    private val repository by lazy {
        HomeRepository(loadState)
    }

    fun loadBannerCo(key:String): MutableLiveData<List<BannerEntity>> {
        val bannerData: MutableLiveData<List<BannerEntity>> = MutableLiveData()
        initiateRequest({
            bannerData.value = repository.loadBannerCo()
        }, loadState,key)
        return  bannerData
    }

    fun loadSeckillGoodsData(): MutableLiveData<List<GoodsEntity>> {
        val seckillGoods:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
        initiateRequest({
            seckillGoods.value = repository.loadSeckillGoodsCo()
        }, loadState)
        return  seckillGoods
    }
}