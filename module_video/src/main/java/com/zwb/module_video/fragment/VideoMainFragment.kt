package com.zwb.module_video.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.module_video.VideoViewModel
import com.zwb.module_video.databinding.FragmentVideoMainBinding
import java.util.*

class VideoMainFragment : BaseFragment<FragmentVideoMainBinding, VideoViewModel>() {

    override val mViewModel by viewModels<VideoViewModel>()
    private val titles = arrayOf("发现", "推荐", "社区")

    override fun FragmentVideoMainBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.darkMode(requireActivity(),true)
        StatusBarUtil.setPaddingSmart(requireContext(), mBinding.constraintLayout)

        val fragments: MutableList<Fragment> = mutableListOf()
        fragments.add(DiscoverFragment())
        fragments.add(RecommendFragment())
        fragments.add(CommunityFragment())

        mBinding.vp2Content.adapter = object : FragmentStateAdapter(this@VideoMainFragment) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }
        TabLayoutMediator(tabLayout, mBinding.vp2Content) { tab, position ->
            tab.text = titles[position]
        }.attach()
        // smoothScroll = true 时 fragment没加载到viewpager2中？？？
        // this.vp2Content.currentItem = 1
        this.vp2Content.setCurrentItem(1,false)
    }


    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}