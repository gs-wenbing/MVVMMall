package com.zwb.module_oder.activity

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_base.utils.StatusBarUtil
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
        this.rgTabs.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                this.rbAll.id -> LogUtils.e(msg = "全部")
                this.rbNoPay.id -> LogUtils.e(msg = "未付款")
                this.rbNoSend.id -> LogUtils.e(msg = "未发货")
                this.rbNoReceive.id -> LogUtils.e(msg = "未收货")
                this.rbNoComment.id -> LogUtils.e(msg = "未评价")
            }
        }

        mAdapter = OrderAdapter(mOrderMultiList)
        this.rvOrder.layoutManager = LinearLayoutManager(this@OrderListActivity)
        this.rvOrder.adapter = mAdapter
        init(mAdapter, this.rvOrder, this.refreshLayout, this@OrderListActivity)

        mAdapter.setOnItemClickListener { adapter, _, position ->
            if(adapter.getItemViewType(position) == OrderAdapter.TITLE){
                Toast.makeText(this@OrderListActivity.applicationContext, "店铺", Toast.LENGTH_SHORT)
                    .show()
            }else if (adapter.getItemViewType(position) == OrderAdapter.DATA){
                Toast.makeText(this@OrderListActivity.applicationContext, "详情", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {

            }
        }
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeToolbar.toolbar)
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.loadOrderList(pageSize, page, orderStatus).observe(this,{
            loadCompleted(action, list = it)
        })
    }

}