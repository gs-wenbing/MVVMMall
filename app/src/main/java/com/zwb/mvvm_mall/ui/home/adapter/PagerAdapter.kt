package com.zwb.mvvm_mall.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zwb.mvvm_mall.ui.home.view.*

class PagerAdapter (fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment{
        return when (position) {
            0 -> HomePageGoodsFragment()
            1 -> HomePageGoodsFragment1()
            2 -> HomePageLiveFragment()
            3 -> HomePageGoodsFragment3()
            4 -> HomePageSellerShowFragment()
            else -> HomePageSellerShowFragment()
        }
    }

    override fun getItemCount(): Int = 5
}