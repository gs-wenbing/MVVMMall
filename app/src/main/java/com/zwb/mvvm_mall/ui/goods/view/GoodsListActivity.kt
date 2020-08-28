package com.zwb.mvvm_mall.ui.goods.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMActivity
import com.zwb.mvvm_mall.bean.GoodsAttrFilterEntity
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.bean.SearchTagEntity
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.common.utils.dp2px
import com.zwb.mvvm_mall.common.view.PopupWindowList
import com.zwb.mvvm_mall.ui.goods.adapter.GoodsAttrFilterAdapter
import com.zwb.mvvm_mall.ui.goods.adapter.GoodsListAdapter
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import com.zwb.mvvm_mall.ui.search.view.SearchActivity
import kotlinx.android.synthetic.main.activity_goods_list.*
import kotlinx.android.synthetic.main.layout_goodslist_filter.*
import kotlinx.android.synthetic.main.layout_search_toolbar.*


class GoodsListActivity :BaseVMActivity<GoodsViewModel>(){

    private lateinit var mAdapter:GoodsListAdapter
    lateinit var mAttrFilterAdapter:GoodsAttrFilterAdapter
    lateinit var mGridManager:GridLayoutManager
    lateinit var mLinearManager:LinearLayoutManager

    var mGoodsList:MutableList<GoodsEntity>?=null

    private var mToLinear = true
    override val layoutId = R.layout.activity_goods_list
    override fun initView() {
        super.initView()
        StatusBarUtil.darkMode(this,true)
        ivBack.setOnClickListener { finish() }
        tvSearch.visibility = View.GONE
        ivRight.visibility = View.VISIBLE
        ivRight.setOnClickListener {switchList()}
        initAdapter()
        initFilter()
        listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            @SuppressLint("RestrictedApi")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (listRecyclerView.canScrollVertically(-1)) {
                    actionButton.visibility = View.VISIBLE
                } else {
                    //滑动到顶部
                    actionButton.visibility = View.INVISIBLE
                }
            }
        })

        actionButton.setOnClickListener {
            listRecyclerView.scrollToPosition(0)
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.loadSeckillGoodsData()
        mViewModel.loadFilterAttrsData()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSeckillGoods.observe(this, Observer {
            mGoodsList = it.toMutableList()
            mAdapter.setNewData(mGoodsList)
        })
        mViewModel.mFilterAttrs.observe(this, Observer {
            mAttrFilterAdapter.setNewData(it.toMutableList())
        })
    }

    private fun initFilter(){
        mAttrFilterAdapter = GoodsAttrFilterAdapter(null)
        hRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        hRecyclerView.adapter = mAttrFilterAdapter
        mAttrFilterAdapter.setOnItemClickListener { adapter, view, position ->
            val popWindow = PopupWindowList(this)
            val goodsAttrFilter = adapter.data[position] as GoodsAttrFilterEntity
            if(goodsAttrFilter.subAttrList!=null && goodsAttrFilter.subAttrList!!.isNotEmpty()){
                popWindow.setData(goodsAttrFilter.subAttrList!!)
                popWindow.showAsDropDown(view)
                popWindow.applyDim(0.5f,listRecyclerView)
                goodsAttrFilter.isSelected = true
                popWindow.setOnPopupListener(object :PopupWindowList.OnPopupListener{
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
        mAdapter = GoodsListAdapter(R.layout.item_goods_commen_layout,null)
        mLinearManager = LinearLayoutManager(this)
        mGridManager = GridLayoutManager(this, 2)
        listRecyclerView.layoutManager = mGridManager
        listRecyclerView.adapter = mAdapter
    }
    private fun switchList(){
        mToLinear = if(mToLinear){
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
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsDetailActivity.launch(this, (adapter.getItem(position) as GoodsEntity).goodsName)
        }
    }
    companion object{
        fun launch(activity: FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, GoodsListActivity::class.java))
            }
    }
}