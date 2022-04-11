package com.zwb.lib_common.service.me

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider


interface IMeService: IProvider {
    fun getFragment(): Fragment
}