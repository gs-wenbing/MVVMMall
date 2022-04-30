package com.zwb.module_oder.activity

import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.module_oder.OrderViewModel
import com.zwb.module_oder.databinding.ActivityOrderBinding
import com.zwb.module_oder.fragment.OrderFragment

@Route(path = RoutePath.Order.PAGE_ORDER_LIST)
class OrderListActivity : BaseActivity<ActivityOrderBinding, OrderViewModel>() {

    override val mViewModel by viewModels<OrderViewModel>()

    private val tabList = ArrayList<RadioButton>()

    @JvmField
    @Autowired(name = Constants.Order.PARAMS_ORDER_STATUS)
    var orderStatus: Int = 0

    override fun ActivityOrderBinding.initView() {

        this.orderViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val fragmentList: MutableList<Class<*>> = mutableListOf()
        for (i in 0 until 5) {
            fragmentList.add(OrderFragment::class.java)
        }
        this.orderViewPager.adapter = object : FragmentStateAdapter(this@OrderListActivity) {

            override fun getItemCount(): Int = fragmentList.size

            override fun createFragment(position: Int): Fragment {
                return try {
                    orderStatus = if (position == 0) Constants.Order.ORDER_ALL else position
                    val fragment = fragmentList[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putInt(Constants.Order.PARAMS_ORDER_STATUS, orderStatus)
                    fragment.arguments = bundle
                    return fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
        }
        tabList.add(this.rbAll)
        tabList.add(this.rbNoPay)
        tabList.add(this.rbNoReceive)
        tabList.add(this.rbNoSend)
        tabList.add(this.rbNoComment)
        val tabClickListener = View.OnClickListener {
            this.orderViewPager.currentItem = tabList.indexOf(it)
        }
        for (itemTab in tabList) {
            itemTab.setOnClickListener(tabClickListener)
        }

        mBinding.orderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                onTabChanged(position)
            }
        })
        val index = if (orderStatus == Constants.Order.ORDER_ALL) 0 else orderStatus
        mBinding.orderViewPager.currentItem = index

        this.includeToolbar.ivBack.setOnClickListener {
            finish()
        }
        this.includeToolbar.tvSearch.setOnClickListener {
            Toast.makeText(applicationContext, "搜索", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onTabChanged(position: Int) {
        val num = tabList.size
        for (i in 0 until num) {
            val itemTab = tabList[i]
            if (i == position) {
                itemTab.setTextColor(Color.parseColor("#ff0000"))
                itemTab.paint.isFakeBoldText = true
                itemTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            } else {
                itemTab.setTextColor(Color.parseColor("#333333"))
                itemTab.paint.isFakeBoldText = false
                itemTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            }
        }
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeToolbar.toolbar)
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }


}