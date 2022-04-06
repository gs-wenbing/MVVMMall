package com.zwb.module_classify

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_classify.databinding.ActivityClassifyBinding
import com.zwb.module_classify.fragment.ClassifyFragment

class ClassifyActivity:BaseActivity<ActivityClassifyBinding,ClassifyViewModel>() {
    override val mViewModel by viewModels<ClassifyViewModel>()

    override fun ActivityClassifyBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent,ClassifyFragment()).commit()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }


}
