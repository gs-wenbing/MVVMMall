package com.zwb.module_cart

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_cart.databinding.CartActivityBinding
import com.zwb.module_cart.fragment.CartFragment

class CartActivity : BaseActivity<CartActivityBinding,CartViewModel>() {
    override val mViewModel by viewModels<CartViewModel>()

    override fun CartActivityBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = CartFragment()
        transaction.add(R.id.frameContext,fragment).commit()
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }


}
