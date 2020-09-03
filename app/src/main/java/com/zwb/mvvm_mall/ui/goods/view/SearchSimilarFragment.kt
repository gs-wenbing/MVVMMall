package com.zwb.mvvm_mall.ui.goods.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.common.utils.KeyBoardUtils
import com.zwb.mvvm_mall.ui.goods.adapter.SearchSimilarAdapter
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_search_similar.*

/**
 * 搜索推荐
 */
class SearchSimilarFragment :BaseVMFragment<GoodsViewModel>(){
    override var layoutId = R.layout.fragment_search_similar

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
        rvSimilar.layoutManager = LinearLayoutManager(mActivity)
        val similarAdapter = SearchSimilarAdapter(arrayOf("格子裤女直筒"
            ,"格子裤女直筒2"
            ,"格子裤女直筒3"
            ,"格子裤女直筒4"
            ,"格子裤女直筒5"
            ,"格子裤女直筒6"
            ,"格子裤女直筒7"
            ,"格子裤女直筒8"
            ,"格子裤女直筒9"
            ,"格子裤女直筒10"
            ,"格子裤女直筒11"
            ,"格子裤女直筒12").toList().toMutableList())
        rvSimilar.adapter = similarAdapter
        similarAdapter.setOnItemClickListener { adapter, _, position ->
            KeyBoardUtils.hideInputForce(mActivity)
            mViewModel.mSearchKey.value = adapter.data[position].toString()
            mViewModel.mSearchStatus.value = SearchGoodsActivity.SearchStatus_GOODS
        }
        rvSimilar.setOnTouchListener { _, _ ->
            KeyBoardUtils.hideInputForce(mActivity)
            false
        }
    }

    override fun initDataObserver() {
        super.initDataObserver()
    }

    companion object {
        fun newInstance(): SearchSimilarFragment {
            return SearchSimilarFragment()
        }
    }
}