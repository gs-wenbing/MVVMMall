package com.zwb.lib_common.service.home

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider


interface IHomeService: IProvider {
    fun getFragment():Fragment

}