package com.zwb.module_login.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_login.LoginViewModel
import com.zwb.module_login.R
import com.zwb.module_login.databinding.LoginActivityLoginBinding
import com.zwb.module_login.fragment.RegisterFragment

class LoginActivity:BaseActivity<LoginActivityLoginBinding,LoginViewModel>() {
    override val mViewModel by viewModels<LoginViewModel>()

    override fun LoginActivityLoginBinding.initView() {
        val registerFragment = RegisterFragment()
        val transaction = supportFragmentManager.beginTransaction()
        mBinding.btnRegister.setOnClickListener {
            transaction.add(R.id.frameContent,registerFragment);
            transaction.commit()
        }
        mBinding.btnLogin.setOnClickListener {
            
        }
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
    }
}