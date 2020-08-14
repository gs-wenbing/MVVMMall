package com.zwb.mvvm_mall.ui.classify.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.network.dataConvert
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.bean.ClassifyEntity


class ClassifyRepository(private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun loadClassifyCo(): List<ClassifyEntity> {
        return apiService.getGoodsClassCo().dataConvert(loadState)
    }

}

