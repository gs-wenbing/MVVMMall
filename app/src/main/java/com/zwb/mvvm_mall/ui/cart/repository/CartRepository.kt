package com.zwb.mvvm_mall.ui.cart.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.bean.CartGoodsEntity
import com.zwb.mvvm_mall.bean.CartLikeGoodsEntity
import com.zwb.mvvm_mall.network.dataConvert

class CartRepository (private val loadState: MutableLiveData<State>): BaseRepository() {


    suspend fun getCartList(): List<CartGoodsEntity> {
        return apiService.getCartList().dataConvert(loadState)
    }
    suspend fun getCartLikeGoods(): List<CartLikeGoodsEntity> {
        return apiService.getCartLikeGoods().dataConvert(loadState)
    }
}