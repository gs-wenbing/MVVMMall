package com.zwb.lib_common.service.classify

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IClassifyService:IProvider {
    fun getFragment(): Fragment
}