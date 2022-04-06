package com.zwb.module_login.fragment

import androidx.fragment.app.viewModels
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_login.LoginViewModel
import com.zwb.module_login.databinding.LoginFragmentRegisterBinding

class RegisterFragment:BaseFragment<LoginFragmentRegisterBinding, LoginViewModel>() {
    override val mViewModel by viewModels<LoginViewModel>()

    override fun LoginFragmentRegisterBinding.initView() {
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}