package com.zwb.mvvm_mall.ui.goods.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.BannerResponse
import com.zwb.mvvm_mall.bean.CommentEntity
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.ui.goods.repository.GoodsListRepository

class GoodsViewModel :BaseViewModel<GoodsListRepository>(){

    var mCommentList: MutableLiveData<List<CommentEntity>> = MutableLiveData()
    fun loadCommentList() {
        initiateRequest({
            mCommentList.value = mRepository.loadCommentList()
        }, loadState)
    }

    var mSeckillGoods:MutableLiveData<List<GoodsEntity>> = MutableLiveData()
    fun loadSeckillGoodsData(){
        initiateRequest({
            mSeckillGoods.value = mRepository.loadGoodsList()
        }, loadState)
    }
}