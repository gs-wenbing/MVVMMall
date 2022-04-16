package com.zwb.module_cart

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.module_cart.baen.CartGoodsEntity

class CartViewModel:BaseViewModel() {

    private val repo by lazy { CartRepository(loadState) }

    var mCartGoodsData: MutableLiveData<List<MultiItemEntity>> = MutableLiveData()
    var mCartLikeGoodsData: MutableLiveData<List<MultiItemEntity>> = MutableLiveData()

    fun loadCartGoodsData(){
        initiateRequest({
            mCartGoodsData.value =  repo.getCartList(CartApi.CART_URL)
            mCartLikeGoodsData.value =  repo.getCartLikeGoods(CartApi.CART_LIKE_URL)
        }, loadState, CartApi.CART_URL)
    }
}