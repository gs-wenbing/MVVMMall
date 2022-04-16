package com.zwb.module_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.bean.BannerEntity

class HomeViewModel : BaseViewModel() {

    private val repository by lazy {
        HomeRepository(loadState)
    }

    fun loadBannerCo(): MutableLiveData<List<BannerEntity>> {
        val bannerData: MutableLiveData<List<BannerEntity>> = MutableLiveData()
        initiateRequest({
            bannerData.value = repository.loadBannerCo(HomeApi.BANNER_URL)
        }, loadState, HomeApi.BANNER_URL)
        return bannerData
    }

    fun loadSeckillGoodsData(): MutableLiveData<List<GoodsEntity>> {
        val seckillGoods: MutableLiveData<List<GoodsEntity>> = MutableLiveData()
        initiateRequest({
            seckillGoods.value = repository.loadSeckillGoodsCo(HomeApi.SECKILL_URL)
        }, loadState, HomeApi.SECKILL_URL)
        return seckillGoods
    }


    fun loadBoutiqueGoodsCo(): MutableLiveData<List<GoodsEntity>> {
        val boutiqueGoods: MutableLiveData<List<GoodsEntity>> = MutableLiveData()
        initiateRequest({
            boutiqueGoods.value = repository.loadBoutiqueGoodsCo(HomeApi.BOUTIQUE_URL)
        }, loadState, HomeApi.BOUTIQUE_URL)
        return boutiqueGoods
    }
}