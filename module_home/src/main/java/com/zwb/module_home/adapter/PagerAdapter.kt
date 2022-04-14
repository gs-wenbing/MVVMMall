package com.zwb.module_home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter (private var fragmentList:ArrayList<Class<*>>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment{
        return try {
            fragmentList[position].newInstance() as Fragment;
        } catch (e:Exception) {
            e.printStackTrace();
            Fragment()
        }
//        return when (position) {
//            0 -> HomePageGoodsFragment1()
//            1 -> HomePageGoodsFragment1()
//            2 -> HomePageGoodsFragment1()
//            3 -> HomePageGoodsFragment1()
//            4 -> HomePageGoodsFragment1()
//            else -> HomePageGoodsFragment1()
//        }
    }

    override fun getItemCount(): Int = fragmentList.size

}