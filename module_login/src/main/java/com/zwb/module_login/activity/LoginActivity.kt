package com.zwb.module_login.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.bean.StringEvent
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.constant.SpKey
import com.zwb.module_login.LoginViewModel
import com.zwb.module_login.R
import com.zwb.module_login.databinding.LoginActivityLoginBinding
import com.zwb.module_login.fragment.RegisterFragment


@Route(path = RoutePath.Login.PAGE_LOGIN)
class LoginActivity:BaseActivity<LoginActivityLoginBinding, LoginViewModel>(), View.OnFocusChangeListener {

    override val mViewModel by viewModels<LoginViewModel>()

    @JvmField
    @Autowired
    var path: String? = null

    override fun LoginActivityLoginBinding.initView() {
        ARouter.getInstance().inject(this@LoginActivity)
        mBinding.editUsername.onFocusChangeListener = this@LoginActivity
        mBinding.editPassword.onFocusChangeListener = this@LoginActivity

        val registerFragment = RegisterFragment()
        val transaction = supportFragmentManager.beginTransaction()
        mBinding.btnRegister.setOnClickListener {
            transaction.add(R.id.frameContent, registerFragment)
            transaction.commit()
        }
        mBinding.btnLogin.setOnClickListener {
            val sign2 = mBinding.editUsername.text.isNotEmpty()
            val sign3 = mBinding.editPassword.text.isNotEmpty()
            if (sign2 and sign3) {
                SpUtils.put(SpKey.IS_LOGIN, true)
                Toast.makeText(applicationContext,"登录成功",Toast.LENGTH_LONG).show()
                if (!TextUtils.isEmpty(path)) {
                    when(path){
                        RoutePath.Cart.FRAGMENT_CART -> EventBusUtils.postEvent(StringEvent(StringEvent.Event.SWITCH_CART))
                        RoutePath.Me.FRAGMENT_ME -> EventBusUtils.postEvent(StringEvent(StringEvent.Event.SWITCH_ME))
                        else -> ARouter.getInstance().build(path)
                            .with(intent.extras)
                            .navigation()
                    }
                }
                finish()
            } else {
                Toast.makeText(applicationContext,"用户名或密码为空",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v.id) {
            R.id.editUsername// 账号输入框
            -> if (hasFocus) {
                // 此处为得到焦点时的处理内容
                mBinding.ivUser.setBackgroundResource(R.mipmap.username_focus)
            } else {
                // 此处为失去焦点时的处理内容
                mBinding.ivUser.setBackgroundResource(R.mipmap.username)
            }
            R.id.editPassword// 密码输入框
            -> if (hasFocus) {
                // 此处为得到焦点时的处理内容
                mBinding.ivPassword.setBackgroundResource(R.mipmap.password_focus)
            } else {
                // 此处为失去焦点时的处理内容
                mBinding.ivPassword.setBackgroundResource(R.mipmap.password)
            }
        }
    }

    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
    }


}