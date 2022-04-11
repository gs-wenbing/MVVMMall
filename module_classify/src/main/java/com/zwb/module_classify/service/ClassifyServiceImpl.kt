package com.zwb.module_classify.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.classify.IClassifyService
import com.zwb.module_classify.fragment.ClassifyFragment

@Route(path = RoutePath.Classify.SERVICE_CLASSIFY)
class ClassifyServiceImpl:IClassifyService {

    override fun getFragment(): Fragment {
        return ClassifyFragment()
    }

    override fun init(context: Context?) {

    }
}