package com.zwb.mvvm_mall.ui.cart.viewmodel

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.ui.cart.repository.CartRepository

class CartViewModel : BaseViewModel<CartRepository>() {

    var mCartGoodsData:MutableLiveData<List<MultiItemEntity>> = MutableLiveData()
    var mCartLikeGoodsData:MutableLiveData<List<MultiItemEntity>> = MutableLiveData()

    fun loadCartGoodsData(){
        initiateRequest({
            mCartGoodsData.value =  mRepository.getCartList()
            mCartLikeGoodsData.value =  mRepository.getCartLikeGoods()
        }, loadState)
    }

}