package com.zwb.mvvm_mall.ui.classify.repository

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.base.repository.BaseRepository
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.vm.UnPeekLiveData
import com.zwb.mvvm_mall.bean.ClassifyEntity
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.network.dataConvert


class ClassifyRepository(private val loadState: MutableLiveData<State>): BaseRepository() {

    suspend fun loadClassifyCo(key:String): List<ClassifyEntity> {
        return apiService.getGoodsClassCo().dataConvert(loadState, key)
    }

}

