package com.zwb.module_cart

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.module_cart.baen.CartGoodsEntity
import com.zwb.module_cart.baen.CartLikeGoodsEntity

class CartRepository(private val loadState: MutableLiveData<State>):BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(CartApi::class.java, CartApi.BASE_URL)
    }
    suspend fun getCartList(key:String): List<CartGoodsEntity> {
        return apiService.getCartList().dataConvert(loadState, key)
    }
    suspend fun getCartLikeGoods(key:String): List<CartLikeGoodsEntity> {
        return apiService.getCartLikeGoods().dataConvert(loadState, key)
    }
}