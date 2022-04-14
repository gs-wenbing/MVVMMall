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

    fun loadCartGoodsData(key:String){
        initiateRequest({
            mCartGoodsData.value =  ArrayList<CartGoodsEntity>()//repo.getCartList(key)
            mCartLikeGoodsData.value =  repo.getCartLikeGoods(key)
        }, loadState,key)
    }
}