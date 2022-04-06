package com.zwb.module_home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zwb.module_home.fragment.HomePageGoodsFragment1

class PagerAdapter (fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment{
        return when (position) {
            0 -> HomePageGoodsFragment1()
            1 -> HomePageGoodsFragment1()
            2 -> HomePageGoodsFragment1()
            3 -> HomePageGoodsFragment1()
            4 -> HomePageGoodsFragment1()
            else -> HomePageGoodsFragment1()
        }
    }

    override fun getItemCount(): Int = 5
}