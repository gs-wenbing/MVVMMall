package com.zwb.module_video.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.video.IVideoService
import com.zwb.module_video.fragment.VideoMainFragment

@Route(path = RoutePath.Video.SERVICE_VIDEO)
class VideoServiceImpl: IVideoService {

    override fun getFragment(): Fragment {
        return VideoMainFragment()
    }

    override fun init(context: Context?) {

    }
}