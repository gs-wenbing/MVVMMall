package com.zwb.mvvm_mall.ui.classify.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zwb.mvvm_mall.network.initiateRequest
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.bean.ClassifyEntity
import com.zwb.mvvm_mall.bean.ClassifySectionEntity
import com.zwb.mvvm_mall.ui.classify.repository.ClassifyRepository

class ClassifyViewModel : BaseViewModel<ClassifyRepository>() {


    var mClassifyData: MutableLiveData<List<ClassifyEntity>> = MutableLiveData()
    var mClassifyRightData: MutableLiveData<List<ClassifySectionEntity>> = MutableLiveData()
    var mCurrClassify: MutableLiveData<ClassifyEntity> = MutableLiveData()


    fun loadClassifyCo(key:String) {
        initiateRequest({
            mClassifyData.value = mRepository.loadClassifyCo(key)
        }, loadState)
    }

    fun loadRightClassify(classify: ClassifyEntity) {
        val list = classify.children
        val classifyList:MutableList<ClassifySectionEntity> = ArrayList()
        classifyList.add(
            ClassifySectionEntity(
                true,
                classify.goodsClassName
            )
        )
        list?.forEach {
            classifyList.add(
                ClassifySectionEntity(
                    ClassifyEntity(
                        it.goodsClassID, classify.goodsClassID,
                        it.goodsClassName, it.picURL, it.children, it.isSelected
                    )
                )
            )
        }
        mClassifyRightData.value = classifyList
    }
}