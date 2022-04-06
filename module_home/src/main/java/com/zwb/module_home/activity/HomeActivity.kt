package com.zwb.module_home.activity

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_home.HomeViewModel
import com.zwb.module_home.R
import com.zwb.module_home.databinding.HomeActivityHomeBinding
import com.zwb.module_home.fragment.HomeFragment


class HomeActivity : BaseActivity<HomeActivityHomeBinding, HomeViewModel>() {

    override val mViewModel by viewModels<HomeViewModel>()

    override fun HomeActivityHomeBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = HomeFragment()
        transaction.add(R.id.content,fragment).commit()
    }
    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}
