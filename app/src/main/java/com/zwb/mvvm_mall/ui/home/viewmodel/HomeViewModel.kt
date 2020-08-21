package com.zwb.mvvm_mall.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.ui.home.repository.HomeRepository
import java.util.HashMap

class HomeViewModel : BaseViewModel<HomeRepository>() {

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
    //精品数据
    var mRecyclerGoods0:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadRecyclerGoodsData0(){
        initiateRequest({
            mRecyclerGoods0.value = mRepository.loadSeckillGoodsCo()
        }, loadState)
    }

    //新品
    var mRecyclerGoods1:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadRecyclerGoodsData1(){
        initiateRequest({
            mRecyclerGoods1.value = mRepository.loadSeckillGoodsCo()
        }, loadState)
    }
    //实惠
    var mRecyclerGoods2:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadRecyclerGoodsData2(){
        initiateRequest({
            mRecyclerGoods2.value = mRepository.loadSeckillGoodsCo()
        }, loadState)
    }
    //直播
    var mRecyclerGoods3:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadRecyclerGoodsData3(){
        initiateRequest({
            mRecyclerGoods3.value = mRepository.loadSeckillGoodsCo()
        }, loadState)
    }
    //直播
    var mRecyclerGoods4:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadRecyclerGoodsData4(){
        initiateRequest({
            mRecyclerGoods4.value = mRepository.loadSeckillGoodsCo()
        }, loadState)
    }
    var mHomeRecyclerViewData:MutableLiveData<HashMap<String, List<GoodsEntity>>> = MutableLiveData()
    fun loadListData(){

    }
}