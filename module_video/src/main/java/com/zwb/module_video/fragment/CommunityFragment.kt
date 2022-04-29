package com.zwb.module_video.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_video.VideoApi
import com.zwb.module_video.VideoViewModel
import com.zwb.module_video.adapter.CommunityAdapter
import com.zwb.module_video.databinding.FragmentCommunityBinding

class CommunityFragment: BaseFragment<FragmentCommunityBinding, VideoViewModel>() {
    override val mViewModel by viewModels<VideoViewModel>()
    private lateinit var mAdapter: CommunityAdapter

    override fun FragmentCommunityBinding.initView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        this.rvCommunity.layoutManager = layoutManager
        mAdapter = CommunityAdapter(this@CommunityFragment, mutableListOf())
        this.rvCommunity.adapter = mAdapter
        this.refreshLayout.setRefreshHeader(ClassicsHeader(context))
        this.refreshLayout.setOnRefreshListener {
            initRequestData()
        }
        mAdapter.setOnLoadMoreListener({
            loadMore()
        }, this.rvCommunity)
        setDefaultLoad(this.refreshLayout, VideoApi.COMMUNITY_URL)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getCommunity().observe(this, {
            mAdapter.setNewData(it)
            mBinding.refreshLayout.finishRefresh()
        })
    }

    private fun loadMore() {
        mViewModel.getCommunityNextPage().observe(this, {
            if (it.isEmpty()) {
                mAdapter.loadMoreEnd()
            } else {
                mAdapter.addData(it)
                mAdapter.loadMoreComplete()
            }

        })
    }
}