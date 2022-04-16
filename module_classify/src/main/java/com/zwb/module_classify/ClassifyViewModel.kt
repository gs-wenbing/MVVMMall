package com.zwb.module_classify

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.module_classify.bean.ClassifyEntity
import com.zwb.module_classify.bean.ClassifySectionEntity

class ClassifyViewModel:BaseViewModel() {

    private val repo by lazy { ClassifyRepo(loadState) }

    var mClassifyData: MutableLiveData<List<ClassifyEntity>> = MutableLiveData()
    var mClassifyRightData: MutableLiveData<List<ClassifySectionEntity>> = MutableLiveData()
    var mCurrClassify: MutableLiveData<ClassifyEntity> = MutableLiveData()


    fun loadClassifyCo() {
        initiateRequest({
            mClassifyData.value = repo.loadClassifyCo(ClassifyApi.CLASS_URL)
        }, loadState, ClassifyApi.CLASS_URL)
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