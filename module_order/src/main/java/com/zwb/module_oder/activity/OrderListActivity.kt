package com.zwb.module_oder.activity

import android.util.Log
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.R
import com.zwb.module_oder.databinding.ActivityOrderBinding
import com.zwb.module_oder.fragment.OrderFragment

@Route(path = RoutePath.Order.PAGE_ORDER_LIST)
class OrderListActivity : BaseActivity<ActivityOrderBinding, OrderViewModel>() {

    override val mViewModel by viewModels<OrderViewModel>()

    @JvmField
    @Autowired(name = Constants.Order.PARAMS_ORDER_STATUS)
    var orderStatus: Int = 0

    override fun ActivityOrderBinding.initView() {
        ARouter.getInstance().inject(this@OrderListActivity)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent, OrderFragment()).commit()
        LogUtils.e(msg=orderStatus.toString())
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

}