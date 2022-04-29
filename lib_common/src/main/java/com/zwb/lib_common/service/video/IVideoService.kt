package com.zwb.lib_common.service.video

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IVideoService : IProvider {
    fun getFragment(): Fragment
}