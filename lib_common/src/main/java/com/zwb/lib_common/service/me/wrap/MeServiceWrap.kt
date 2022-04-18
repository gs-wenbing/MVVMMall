package com.zwb.lib_common.service.me.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.me.IMeService

class MeServiceWrap {
    @Autowired(name = RoutePath.Me.SERVICE_ME)
    lateinit var service: IMeService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        LogUtils.e(msg = "me fragment loading")
        return service.getFragment()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = MeServiceWrap()
        }
    }
}