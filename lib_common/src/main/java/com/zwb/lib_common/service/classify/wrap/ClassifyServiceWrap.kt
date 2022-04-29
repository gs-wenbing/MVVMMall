package com.zwb.lib_common.service.classify.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.classify.IClassifyService

class ClassifyServiceWrap private constructor() {

    @Autowired(name = RoutePath.Classify.SERVICE_CLASSIFY)
    lateinit var service: IClassifyService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment():Fragment{
        LogUtils.e(msg = "Classify fragment loading")
        return service.getFragment()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = ClassifyServiceWrap()
        }
    }

}