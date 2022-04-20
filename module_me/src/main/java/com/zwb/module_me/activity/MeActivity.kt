package com.zwb.module_me.activity

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_me.MeViewModel
import com.zwb.module_me.R
import com.zwb.module_me.databinding.ActivityMeBinding
import com.zwb.module_me.fragment.MeFragment

class MeActivity : BaseActivity<ActivityMeBinding, MeViewModel>() {

    override val mViewModel by viewModels<MeViewModel>()

    override fun ActivityMeBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent, MeFragment()).commit()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}
