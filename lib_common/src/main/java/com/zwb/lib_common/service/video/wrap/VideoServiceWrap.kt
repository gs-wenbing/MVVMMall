package com.zwb.lib_common.service.video.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.video.IVideoService

class VideoServiceWrap {
    @Autowired(name = RoutePath.Video.SERVICE_VIDEO)
    lateinit var service: IVideoService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        LogUtils.e(msg = "video fragment loading")
        return service.getFragment()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = VideoServiceWrap()
        }
    }
}