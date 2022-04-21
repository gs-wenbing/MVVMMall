package com.zwb.module_oder.activity

import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.isGone
import com.zwb.lib_base.ktx.isVisible
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.base.BaseListActivity
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.R
import com.zwb.module_oder.adapter.OrderAdapter
import com.zwb.module_oder.bean.OrderTitleEntity
import com.zwb.module_oder.databinding.ActivityOrderBinding

@Route(path = RoutePath.Order.PAGE_ORDER_LIST)
class OrderListActivity :
    BaseListActivity<MultiItemEntity, ActivityOrderBinding, OrderViewModel>() ,
    BaseListActivity.RecyclerListener {

    override val mViewModel by viewModels<OrderViewModel>()

    private lateinit var mAdapter: OrderAdapter

    private var mOrderSelectList: MutableList<OrderTitleEntity> = ArrayList()

    @JvmField
    @Autowired(name = Constants.Order.PARAMS_ORDER_STATUS)
    var orderStatus: Int = 0

    override fun ActivityOrderBinding.initView() {
        mAdapter = OrderAdapter(mutableListOf(), orderStatus)
        this.rvOrder.layoutManager = LinearLayoutManager(this@OrderListActivity)
        this.rvOrder.adapter = mAdapter
        init(mAdapter, this.rvOrder, this.refreshLayout, this@OrderListActivity)
        this.includeToolbar.ivBack.setOnClickListener {
            finish()
        }
        this.includeToolbar.tvSearch.setOnClickListener {
            Toast.makeText(applicationContext, "搜索", Toast.LENGTH_SHORT).show()
        }

        this.rgTabs.setOnCheckedChangeListener { _, checkedId ->
            mOrderSelectList.clear()
            mBinding.rlOrderBottom.gone()
            when (checkedId) {
                this.rbAll.id -> orderStatus = Constants.Order.ORDER_ALL
                this.rbNoPay.id -> orderStatus = Constants.Order.ORDER_NOT_PAY
                this.rbNoSend.id -> orderStatus = Constants.Order.ORDER_NOT_SENT
                this.rbNoReceive.id -> orderStatus = Constants.Order.ORDER_NOT_RECEIVE
                this.rbNoComment.id -> orderStatus = Constants.Order.ORDER_NOT_COMMENT
            }
            mAdapter.setTab(orderStatus)
            initRequestData()
        }

        mAdapter.setOnItemClickListener { adapter, _, position ->
            if (adapter.getItemViewType(position) == OrderAdapter.DATA){
                Toast.makeText(applicationContext, "详情", Toast.LENGTH_SHORT).show()
            }
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.order_shop ->  Toast.makeText(applicationContext, "店铺", Toast.LENGTH_SHORT).show()
                R.id.ivCheck -> onItemCheckClick(adapter.getItem(position) as OrderTitleEntity, position)
            }
        }
        tvPaySubmit.setOnClickListener {
            Toast.makeText(applicationContext, "合并支付", Toast.LENGTH_SHORT).show()
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


    private fun onItemCheckClick(item: OrderTitleEntity, position: Int) {
        item.isSelected = !item.isSelected
        mAdapter.notifyItemChanged(position)
        if (item.isSelected) {
            mOrderSelectList.add(item)
        } else {
            mOrderSelectList.remove(item)
        }
        if (mOrderSelectList.isNotEmpty()) {
            if (mBinding.rlOrderBottom.isGone) mBinding.rlOrderBottom.visible()
        } else {
            if (mBinding.rlOrderBottom.isVisible) mBinding.rlOrderBottom.gone()
        }
        Log.e("mOrderSelectList", mOrderSelectList.toString())
    }
}