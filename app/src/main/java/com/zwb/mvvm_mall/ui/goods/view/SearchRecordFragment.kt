package com.zwb.mvvm_mall.ui.goods.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.SearchTagEntity
import com.zwb.mvvm_mall.common.utils.KeyBoardUtils
import com.zwb.mvvm_mall.common.utils.UIUtils
import com.zwb.mvvm_mall.ui.goods.adapter.SearchRecordAdapter
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_search_record.*
import kotlinx.android.synthetic.main.layout_search_header.view.*

/**
 * 搜索记录
 */
class SearchRecordFragment :BaseVMFragment<GoodsViewModel>(){

    private lateinit var mAdapter: SearchRecordAdapter

    private lateinit var mHeaderView: View

    override var layoutId = R.layout.fragment_search_record

    override fun initView() {
        super.initView()
        mHeaderView = LayoutInflater.from(mActivity).inflate(R.layout.layout_search_header, null)
        mAdapter = SearchRecordAdapter(null)
        rvSearch.layoutManager = LinearLayoutManager(mActivity)
        rvSearch.adapter = mAdapter
        mAdapter.addHeaderView(mHeaderView)
        rvSearch.setOnTouchListener { _, _ ->
            KeyBoardUtils.hideInputForce(mActivity)
            true
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.loadSearchTags()
        mViewModel.loadSearchHotData()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSearchTagsData.observe(this, Observer {
            it?.let {
                setSearchTagView(mHeaderView.flowLayout,it)
                setSearchTagView(mHeaderView.flowLayout2,it)
            }
        })
        mViewModel.mSearchHotData.observe(this, Observer {
            mAdapter.setNewData(it)
        })
    }

    private fun setSearchTagView(flowLayout: ChipGroup, list:List<SearchTagEntity>) {
        flowLayout.removeAllViews()
        list.forEach {
            val tv = TextView(mActivity)
            tv.setBackgroundResource(R.drawable.shape_grey_background)
            tv.setTextColor(mActivity.getColor(R.color.grey_text))
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
                KeyBoardUtils.hideInputForce(mActivity)
                mViewModel.mSearchKey.value = tv.text.toString()
                mViewModel.mSearchStatus.value = SearchGoodsActivity.SearchStatus_GOODS
            }
        }
    }
    companion object {
        fun newInstance(): SearchRecordFragment {
            return SearchRecordFragment()
        }
    }
}