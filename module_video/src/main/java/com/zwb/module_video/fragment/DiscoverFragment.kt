package com.zwb.module_video.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_video.VideoApi
import com.zwb.module_video.VideoViewModel
import com.zwb.module_video.activity.VideoPlayerActivity
import com.zwb.module_video.adapter.DiscoverAdapter2
import com.zwb.module_video.bean.VideoEntity
import com.zwb.module_video.databinding.FragmentDiscoverBinding

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, VideoViewModel>() {
    override val mViewModel by viewModels<VideoViewModel>()

    private lateinit var mAdapter: DiscoverAdapter2

    override fun FragmentDiscoverBinding.initView() {
        this.rvDiscover.layoutManager = LinearLayoutManager(mContext)
        mAdapter = DiscoverAdapter2(this@DiscoverFragment, mutableListOf())
        this.rvDiscover.adapter = mAdapter
        this.refreshLayout.setRefreshHeader(ClassicsHeader(context))
        this.refreshLayout.setOnRefreshListener {
            initRequestData()
        }
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.data[position]
            if(item is VideoEntity){
                VideoPlayerActivity.launch(requireActivity(), item)
            }
        }
        setDefaultLoad(this.refreshLayout, VideoApi.DISCOVERY_URL)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getDiscover().observe(this, {
            mAdapter.setNewData(it)
            mBinding.refreshLayout.finishRefresh()
        })
    }
}