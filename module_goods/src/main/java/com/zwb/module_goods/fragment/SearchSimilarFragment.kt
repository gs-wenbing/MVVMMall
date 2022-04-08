package com.zwb.module_goods.fragment

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.activity.GoodsListActivity
import com.zwb.module_goods.adapter.SearchSimilarAdapter
import com.zwb.module_goods.databinding.FragmentSearchSimilarBinding

class SearchSimilarFragment: BaseFragment<FragmentSearchSimilarBinding, GoodsViewModel>() {
    override val mViewModel by activityViewModels<GoodsViewModel>()

    override fun FragmentSearchSimilarBinding.initView() {
        rvSimilar.layoutManager = LinearLayoutManager(requireActivity())
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
//            KeyBoardUtils.hideInputForce(requireActivity())
            mViewModel.mSearchKey.value = adapter.data[position].toString()
            mViewModel.mSearchStatus.value = GoodsListActivity.SearchStatus_GOODS
        }
        rvSimilar.setOnTouchListener { _, _ ->
//            KeyBoardUtils.hideInputForce(requireActivity())
            false
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}