package com.zwb.module_oder.fragment

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.isGone
import com.zwb.lib_base.ktx.isVisible
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_common.base.BaseListActivity
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.constant.Constants
import com.zwb.module_oder.OrderFragmentViewModel
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.R
import com.zwb.module_oder.adapter.OrderAdapter
import com.zwb.module_oder.bean.OrderTitleEntity
import com.zwb.module_oder.databinding.FragmentOrderBinding

class OrderFragment :
    BaseListFragment<MultiItemEntity, FragmentOrderBinding, OrderFragmentViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<OrderFragmentViewModel>()

    private lateinit var mAdapter: OrderAdapter
    private var mOrderSelectList: MutableList<OrderTitleEntity> = ArrayList()

    var orderStatus: Int = 0

    override fun FragmentOrderBinding.initView() {
        orderStatus = requireArguments().getInt(Constants.Order.PARAMS_ORDER_STATUS)
        mAdapter = OrderAdapter(mutableListOf(), orderStatus)
        this.rvOrder.layoutManager = LinearLayoutManager(mContext)
        this.rvOrder.adapter = mAdapter
        init(mAdapter, this.rvOrder, this.refreshLayout, this@OrderFragment)

        mAdapter.setOnItemClickListener { adapter, _, position ->
            if (adapter.getItemViewType(position) == OrderAdapter.DATA) {
                Toast.makeText(requireContext().applicationContext, "详情", Toast.LENGTH_SHORT).show()
            }
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.order_shop -> Toast.makeText(
                    requireContext().applicationContext,
                    "店铺",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.ivCheck -> onItemCheckClick(
                    adapter.getItem(position) as OrderTitleEntity,
                    position
                )
            }
        }
        tvPaySubmit.setOnClickListener {
            Toast.makeText(requireContext().applicationContext, "合并支付", Toast.LENGTH_SHORT).show()
        }
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        Handler().postDelayed({
            mViewModel.loadOrderList(pageSize, page, orderStatus).observe(this, {
                loadCompleted(action, list = it)
            })
        },1000)
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