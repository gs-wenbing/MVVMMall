package com.zwb.module_goods.fragment

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.chip.ChipGroup
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.BindingReflex
import com.zwb.lib_base.utils.KeyBoardUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.R
import com.zwb.module_goods.activity.GoodsListActivity
import com.zwb.module_goods.adapter.SearchRecordAdapter
import com.zwb.module_goods.bean.SearchTagEntity
import com.zwb.module_goods.databinding.FragmentSearchRecordBinding
import com.zwb.module_goods.databinding.LayoutSearchHeaderBinding

class SearchRecordFragment: BaseFragment<FragmentSearchRecordBinding, GoodsViewModel>() {

    override val mViewModel by activityViewModels<GoodsViewModel>()

    private lateinit var mAdapter: SearchRecordAdapter

    private lateinit var mHeaderBinding: LayoutSearchHeaderBinding

    override fun FragmentSearchRecordBinding.initView() {
        mHeaderBinding = LayoutSearchHeaderBinding.inflate(layoutInflater)
//        mHeaderView = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_search_header, null)
        mAdapter = SearchRecordAdapter(null)
        mBinding.rvSearch.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.rvSearch.adapter = mAdapter
        mAdapter.addHeaderView(mHeaderBinding.root)
        mBinding.rvSearch.setOnTouchListener { _, _ ->
            KeyBoardUtils.hideInputForce(requireActivity())
            true
        }
    }


    override fun initRequestData() {
        mViewModel.loadSearchTags()
        mViewModel.loadSearchHotData()
    }

    override fun initObserve() {
        mViewModel.mSearchTagsData.observe(viewLifecycleOwner, {
            it?.let {
                setSearchTagView(mHeaderBinding.flowLayout, it)
                setSearchTagView(mHeaderBinding.flowLayout2, it)
            }
        })
        mViewModel.mSearchHotData.observe(viewLifecycleOwner, {
            mAdapter.setNewData(it)
        })
    }

    private fun setSearchTagView(flowLayout: ChipGroup, list:List<SearchTagEntity>) {
        flowLayout.removeAllViews()
        list.forEach {
            val tv = TextView(requireActivity())
            tv.setBackgroundResource(R.drawable.shape_grey_background)
            tv.setTextColor(ContextCompat.getColor(requireActivity(),R.color.grey_text))
            tv.setPadding(
                UIUtils.dp2px(15f), UIUtils.dp2px(5f),
                UIUtils.dp2px(15f), UIUtils.dp2px(5f)
            )
            tv.textSize = 14f
            tv.maxLines = 1
            tv.gravity = Gravity.CENTER
            tv.text = it.tagName
            flowLayout.addView(tv)
            tv.setOnClickListener {
                KeyBoardUtils.hideInputForce(requireActivity())
                mViewModel.mSearchKey.value = tv.text.toString()
                mViewModel.mSearchStatus.value = GoodsListActivity.SearchStatus_GOODS
            }
        }
    }
}