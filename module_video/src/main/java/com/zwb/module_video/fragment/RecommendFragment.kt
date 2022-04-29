package com.zwb.module_video.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_video.VideoApi
import com.zwb.module_video.VideoViewModel
import com.zwb.module_video.activity.VideoPlayerActivity
import com.zwb.module_video.adapter.RecommendAdapter
import com.zwb.module_video.bean.VideoEntity
import com.zwb.module_video.databinding.FragmentRecommendBinding

class RecommendFragment : BaseFragment<FragmentRecommendBinding, VideoViewModel>() {

    override val mViewModel by viewModels<VideoViewModel>()

    private lateinit var mAdapter: RecommendAdapter

    override fun FragmentRecommendBinding.initView() {
        this.rvRecommend.layoutManager = LinearLayoutManager(mContext)
        mAdapter = RecommendAdapter(this@RecommendFragment, mutableListOf())
        this.rvRecommend.adapter = mAdapter
        this.refreshLayout.setRefreshHeader(ClassicsHeader(context))
        this.refreshLayout.setOnRefreshListener {
            initRequestData()
        }
        mAdapter.setOnLoadMoreListener({
            loadMore()
        }, this.rvRecommend)

        mAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.data[position]
            if(item is VideoEntity){
                VideoPlayerActivity.launch(requireActivity(), item)
            }
        }

        setDefaultLoad(this.refreshLayout, VideoApi.RECOMMEND_URL)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getRecommend().observe(this, {
            mAdapter.setNewData(it)
            mBinding.refreshLayout.finishRefresh()
        })
    }

    private fun loadMore() {
        mViewModel.getRecommendNextPage().observe(this, {
            if (it.isEmpty()) {
                mAdapter.loadMoreEnd()
            } else {
                mAdapter.addData(it)
                mAdapter.loadMoreComplete()
            }

        })
    }
}