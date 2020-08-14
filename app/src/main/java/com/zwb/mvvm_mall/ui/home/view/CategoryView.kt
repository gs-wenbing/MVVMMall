package com.zwb.mvvm_mall.ui.home.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.ui.home.adapter.HomeGoodsAdapter
import com.zwb.mvvm_mall.common.view.nested.OnUserVisibleChange

class CategoryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,var index:Int) : ChildRecyclerView(context, attrs, defStyleAttr),
    OnUserVisibleChange {


    private val mDataList = ArrayList<GoodsEntity>()

    private var hasLoadData = false

    init {
        initRecyclerView()
        initLoadMore()
    }

    private fun initRecyclerView() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        layoutManager = staggeredGridLayoutManager
        adapter = HomeGoodsAdapter(R.layout.item_goods_big_layout,mDataList)
    }

    private fun initLoadMore() {
        addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                tryLoadMoreIfNeed()
            }
        })

    }

    private fun tryLoadMoreIfNeed() {
        if(adapter == null) return
        val layoutManager = layoutManager
        val last: IntArray
        if (layoutManager is StaggeredGridLayoutManager) {
            last = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(last)
            for (i in last.indices) {
                if ((last[i] >= adapter!!.itemCount - 4)) {
                    if (loadMore()) return
                    break
                }
            }
        }
    }

    private fun initData() {
        hasLoadData = true
        mOnRecyclerViewLoadListener?.onRecyclerViewInitData(index)

    }

    private fun loadMore():Boolean {
        return mOnRecyclerViewLoadListener?.onRecyclerViewLoadMore(index)!!
    }

    override fun setData(isRefrash:Boolean,list: List<GoodsEntity>){
        if (isRefrash){
            mDataList.clear()
            mDataList.addAll(list)
            adapter?.notifyDataSetChanged()
        }else{
            mDataList.addAll(list)
            val loadMoreSize = 5
            adapter?.notifyItemRangeChanged(mDataList.size-loadMoreSize,mDataList.size)
        }
    }

    override fun onUserVisibleChange(isVisibleToUser: Boolean) {
        if(hasLoadData.not() && isVisibleToUser) {
            initData()
        }
    }

}