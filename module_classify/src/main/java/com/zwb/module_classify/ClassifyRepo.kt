package com.zwb.module_classify

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.module_classify.bean.ClassifyEntity

class ClassifyRepo(private val loadState: MutableLiveData<State>):BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(ClassifyApi::class.java, ClassifyApi.BASE_URL)
    }

    suspend fun loadClassifyCo(key:String): List<ClassifyEntity> {
        return apiService.getGoodsClassCo().dataConvert(loadState, key)
    }
}