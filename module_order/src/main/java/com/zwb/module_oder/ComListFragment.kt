package com.zwb.module_oder

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class ComListFragment<T>: Fragment() {
    private lateinit var mRefreshLy: SmartRefreshLayout
    private var mAdapter: BaseQuickAdapter<T, BaseViewHolder>? = null
    private var mRecyclerListener: RecyclerListener? = null
    private var mCurrentPage = 1
    private var mPageSize = 15
    private var mHasCheckNet = true

    private var _binding = null

    interface RecyclerListener {
        /**
         * recyclerView创建完成
         * @param recyclerView 当前创建的recyclerView
         */
        fun onRecyclerCreated(recyclerView: RecyclerView?)
        /**
         * 分页加载数据
         * @param action 当前加载的动作
         * @param pageSize 每页数
         * @param page 第几页
         */
        fun loadListData(action: Int, pageSize: Int, page: Int)
    }
}