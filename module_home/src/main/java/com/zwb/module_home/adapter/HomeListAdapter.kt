package com.zwb.module_home.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.module_home.R
import com.zwb.module_home.fragment.HomePageGoodsFragment1

class HomeListAdapter(var fragment: Fragment, data: MutableList<Any>?) :
    BaseQuickAdapter<Any, BaseViewHolder>(R.layout.home_item_main_tabs, data) {

    private val tabList = ArrayList<TextView>()

    override fun convert(helper: BaseViewHolder, item: Any?) {
        val mainViewPager = helper.getView<ViewPager2>(R.id.mainViewPager)
        mainViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val fragmentList = ArrayList<Class<*>>()
        fragmentList.add(HomePageGoodsFragment1::class.java)
        fragmentList.add(HomePageGoodsFragment1::class.java)
        fragmentList.add(HomePageGoodsFragment1::class.java)
        fragmentList.add(HomePageGoodsFragment1::class.java)
        fragmentList.add(HomePageGoodsFragment1::class.java)
        mainViewPager.adapter = object : FragmentStateAdapter(fragment){

            override fun getItemCount(): Int = fragmentList.size

            override fun createFragment(position: Int): Fragment {
                return try {
                    fragmentList[position].newInstance() as Fragment;
                } catch (e:Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
        }
        //防止内存泄露
        mainViewPager.offscreenPageLimit = 1
        val tabClickListener = View.OnClickListener {
            val index = tabList.indexOf(it)
            mainViewPager.currentItem = index
        }

        tabList.add(helper.getView(R.id.mainTab1))
        tabList.add(helper.getView(R.id.mainTab2))
        tabList.add(helper.getView(R.id.mainTab3))
        tabList.add(helper.getView(R.id.mainTab4))
        tabList.add(helper.getView(R.id.mainTab5))
        for (itemTab in tabList) {
            itemTab.setOnClickListener(tabClickListener)
        }

        mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                onTabChanged(position)
            }
        })
        onTabChanged(0)
    }
    private fun onTabChanged(position: Int) {
        val num = tabList.size
        for (i in 0 until num) {
            val itemTab = tabList[i]
            if (i == position) {
                itemTab.setTextColor(COLOR_TAB_SELECTED)
                itemTab.paint.isFakeBoldText = true
                itemTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            } else {
                itemTab.setTextColor(COLOR_TAB_NORMAL)
                itemTab.paint.isFakeBoldText = false
                itemTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            }
        }
    }
    companion object {
        private val COLOR_TAB_NORMAL by lazy { Color.parseColor("#333333") }
        private val COLOR_TAB_SELECTED by lazy { Color.parseColor("#ff0000") }
    }
}