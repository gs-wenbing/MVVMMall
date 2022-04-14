package com.zwb.module_home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zwb.module_home.fragment.MenuGridFragment

class MenuPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        val menuGridFragment = MenuGridFragment()
        menuGridFragment.page = position
        return menuGridFragment
    }

    override fun getItemCount(): Int = 2
}