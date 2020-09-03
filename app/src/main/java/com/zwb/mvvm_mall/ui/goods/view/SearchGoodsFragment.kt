package com.zwb.mvvm_mall.ui.goods.view

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.GoodsAttrFilterEntity
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.common.view.PopupWindowList
import com.zwb.mvvm_mall.ui.goods.adapter.GoodsAttrFilterAdapter
import com.zwb.mvvm_mall.ui.goods.adapter.GoodsListAdapter
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_search_goodslist.*
import kotlinx.android.synthetic.main.layout_goodslist_filter.*
import android.os.Handler
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.kingja.loadsir.core.LoadSir
import com.zwb.mvvm_mall.common.callback.PlaceHolderCallBack
import com.zwb.mvvm_mall.common.utils.Constant


/**
 * 商品列表页面
 */
class SearchGoodsFragment :BaseVMFragment<GoodsViewModel>(){

    private lateinit var mAdapter: GoodsListAdapter
    private lateinit var mAttrFilterAdapter: GoodsAttrFilterAdapter
    private lateinit var mGridManager: GridLayoutManager
    private  lateinit var mLinearManager: LinearLayoutManager
    var mGoodsList:MutableList<GoodsEntity>?=null
    private var mToGrid = true
    private var isLoadMore = true

    override var layoutId = R.layout.fragment_search_goodslist

    override fun initView() {
        super.initView()
        initAdapter()
        initFilter()
        listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            @SuppressLint("RestrictedApi")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.canScrollVertically(-1)) {
                    actionButton?.visibility = View.VISIBLE
                } else {
                    //滑动到顶部
                    actionButton?.visibility = View.INVISIBLE
                }
            }
        })
        actionButton.setOnClickListener {
            listRecyclerView.scrollToPosition(0)
        }
        registerPlaceHolderLoad(coordinatorLayout, R.layout.layout_placeholder_search_goods)
    }

    private fun loadData(){
        isLoadMore = false
        showPlaceHolder()
        //模拟加载效果
        Handler().postDelayed({
            mViewModel.loadFilterAttrsData()
            mViewModel.loadSeckillGoodsData()
        }, 2000)
    }
    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSeckillGoods.observe(this, Observer {
            if(!isLoadMore){
                showSuccess(Constant.COMMON_KEY)
                mGoodsList = it.toMutableList()
            }else{
                mGoodsList?.addAll(it.toMutableList())
            }
            mAdapter.setNewData(mGoodsList)
        })
        mViewModel.mFilterAttrs.observe(this, Observer {
            mAttrFilterAdapter.setNewData(it.toMutableList())
        })

        mViewModel.mSearchKey.observe(this, Observer {
            loadData()
        })
    }

    override fun reLoad() {
        loadData()
    }

    private fun initFilter(){
        mAttrFilterAdapter = GoodsAttrFilterAdapter(null)
        hRecyclerView.layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false)
        hRecyclerView.adapter = mAttrFilterAdapter
        mAttrFilterAdapter.setOnItemClickListener { adapter, view, position ->
            val popWindow = PopupWindowList(mActivity)
            val goodsAttrFilter = adapter.data[position] as GoodsAttrFilterEntity
            if(goodsAttrFilter.subAttrList!=null && goodsAttrFilter.subAttrList!!.isNotEmpty()){
                popWindow.setData(goodsAttrFilter.subAttrList!!)
                popWindow.showAsDropDown(view)
                popWindow.applyDim(0.5f,listRecyclerView)
                goodsAttrFilter.isSelected = true
                popWindow.setOnPopupListener(object : PopupWindowList.OnPopupListener{
                    override fun onDismiss(isConfirm: Boolean) {
                        if(!isConfirm){
                            popWindow.updateItem(isConfirm = false)
                            goodsAttrFilter.isSelected = false
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onReset() {
                        popWindow.updateItem(isConfirm = false)
                        goodsAttrFilter.selectString = ""
                        adapter.notifyDataSetChanged()
                    }

                    override fun onConfirm(selects:List<GoodsAttrFilterEntity>) {
                        popWindow.dismiss(isConfirm = true)
                        mGoodsList?.clear()
                        goodsAttrFilter.selectString = ""
                        selects.forEach {
                            goodsAttrFilter.selectString += it.attrName+","
                        }
                        adapter.notifyDataSetChanged()
                        isLoadMore = false
                        mViewModel.loadSeckillGoodsData()
                    }
                })
            }else{
                goodsAttrFilter.isSelected = !goodsAttrFilter.isSelected
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initAdapter(){
        mAdapter = GoodsListAdapter(R.layout.item_goods_linear_layout,null)
        mLinearManager = LinearLayoutManager(mActivity)
        mGridManager = GridLayoutManager(mActivity, 2)
        listRecyclerView.layoutManager = mLinearManager
        listRecyclerView.adapter = mAdapter
        setRecyclerViewListener()
    }
    fun switchList(ivRight:ImageView){
        mToGrid = if(mToGrid){
            ivRight.setImageResource(R.mipmap.switch_grid)
            listRecyclerView.layoutManager = mLinearManager
            mAdapter = GoodsListAdapter(R.layout.item_goods_linear_layout,mGoodsList)
            false
        }else{
            ivRight.setImageResource(R.mipmap.switch_list)
            listRecyclerView.layoutManager = mGridManager
            mAdapter = GoodsListAdapter(R.layout.item_goods_commen_layout,mGoodsList)
            true
        }
        listRecyclerView.adapter = mAdapter
        setRecyclerViewListener()
    }

    private fun setRecyclerViewListener(){
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsDetailActivity.launch(mActivity, (adapter.getItem(position) as GoodsEntity).goodsName)
        }
        mAdapter.setOnItemChildClickListener { _, view, _ ->
            when {
                view.id == R.id.tvShop -> Toast.makeText(mActivity,"店铺",Toast.LENGTH_SHORT).show()
            }
        }
        mAdapter.setOnLoadMoreListener({
            Handler().postDelayed({
                isLoadMore = true
                mViewModel.loadSeckillGoodsData()
            }, 2000)
        },listRecyclerView)
    }
    companion object {
        fun newInstance(): SearchGoodsFragment {
            return SearchGoodsFragment()
        }
    }
}