package com.zwb.lib_common.service.cart

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface ICartService: IProvider {
    fun getFragment(): Fragment
}