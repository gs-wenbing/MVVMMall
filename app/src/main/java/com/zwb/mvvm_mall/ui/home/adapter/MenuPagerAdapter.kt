package com.zwb.mvvm_mall.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zwb.mvvm_mall.ui.home.view.MenuGridFragment

class MenuPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val menuGridFragment = MenuGridFragment()
        menuGridFragment.page = position
        return menuGridFragment
    }

    override fun getItemCount(): Int = 2
}