package com.zwb.module_oder.activity

import android.os.Handler
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_common.base.BaseListActivity
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.adapter.OrderAdapter
import com.zwb.module_oder.databinding.ActivityOrderBinding

@Route(path = RoutePath.Order.PAGE_ORDER_LIST)
class OrderListActivity :
    BaseListActivity<MultiItemEntity, ActivityOrderBinding, OrderViewModel>() ,
    BaseListActivity.RecyclerListener {

    override val mViewModel by viewModels<OrderViewModel>()

    private lateinit var mAdapter: OrderAdapter

    private var mOrderMultiList: MutableList<MultiItemEntity> = ArrayList()

    @JvmField
    @Autowired(name = Constants.Order.PARAMS_ORDER_STATUS)
    var orderStatus: Int = 0

    override fun ActivityOrderBinding.initView() {
        LogUtils.e(msg = orderStatus.toString())
        mBinding.rgTabs.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                mBinding.rbAll.id -> LogUtils.e(msg = "全部")
                mBinding.rbNoPay.id -> LogUtils.e(msg = "未付款")
                mBinding.rbNoSend.id -> LogUtils.e(msg = "未发货")
                mBinding.rbNoReceive.id -> LogUtils.e(msg = "未收货")
                mBinding.rbNoComment.id -> LogUtils.e(msg = "未评价")
            }
        }

        mAdapter = OrderAdapter(mOrderMultiList)
        mBinding.rvOrder.layoutManager = LinearLayoutManager(this@OrderListActivity)
        mBinding.rvOrder.adapter = mAdapter
        init(mAdapter, mBinding.rvOrder, mBinding.refreshLayout, this@OrderListActivity)
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        Handler().postDelayed({
            mViewModel.loadOrderList(pageSize, page).observe(this,{
                loadCompleted(action, list = it)
            })
        },1000)
    }

}