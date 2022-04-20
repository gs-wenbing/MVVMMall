package com.zwb.module_me.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.me.IMeService
import com.zwb.module_me.fragment.MeFragment

@Route(path = RoutePath.Me.SERVICE_ME)
class MeServiceImpl:IMeService {

    override fun getFragment(): Fragment {
        return MeFragment()
    }

    override fun init(context: Context?) {

    }
}