package com.zwb.module_goods.fragment

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.R
import com.zwb.module_goods.activity.GoodsDetailActivity
import com.zwb.module_goods.adapter.GoodsAttrFilterAdapter
import com.zwb.module_goods.adapter.GoodsListAdapter
import com.zwb.module_goods.bean.GoodsAttrFilterEntity
import com.zwb.module_goods.databinding.FragmentSearchGoodsBinding
import com.zwb.module_goods.view.PopupWindowList

class SearchGoodsFragment :
    BaseListFragment<GoodsEntity, FragmentSearchGoodsBinding, GoodsViewModel>() , BaseListFragment.RecyclerListener {
    override val mViewModel by activityViewModels<GoodsViewModel>()

    private lateinit var mAdapter: GoodsListAdapter
    private lateinit var mAttrFilterAdapter: GoodsAttrFilterAdapter
    private lateinit var mGridManager: GridLayoutManager
    private lateinit var mLinearManager: LinearLayoutManager
    var mGoodsList: MutableList<GoodsEntity>? = null
    private var mToGrid = true

    override fun FragmentSearchGoodsBinding.initView() {
        initAdapter()
        initFilter()
        mBinding.listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            @SuppressLint("RestrictedApi")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.canScrollVertically(-1)) {
                    mBinding.actionButton.visibility = View.VISIBLE
                } else {
                    //滑动到顶部
                    mBinding.actionButton.visibility = View.INVISIBLE
                }
            }
        })
        mBinding.actionButton.setOnClickListener {
            mBinding.listRecyclerView.scrollToPosition(0)
        }
        init(mAdapter, mBinding.listRecyclerView, mBinding.refreshLayout, this@SearchGoodsFragment)
    }

    private fun initAdapter() {
        mAdapter = GoodsListAdapter(R.layout.item_goods_linear_layout, null)
        mLinearManager = LinearLayoutManager(requireActivity())
        mGridManager = GridLayoutManager(requireActivity(), 2)
        mBinding.listRecyclerView.layoutManager = mLinearManager
        mBinding.listRecyclerView.adapter = mAdapter
        setRecyclerViewListener()
    }

    /**
     * 切换列表显示样式
     */
    fun switchList(ivRight: ImageView) {
        mToGrid = if (mToGrid) {
            ivRight.setImageResource(R.mipmap.switch_grid)
            mBinding.listRecyclerView.layoutManager = mLinearManager
            mAdapter = GoodsListAdapter(R.layout.item_goods_linear_layout, mGoodsList)
            false
        } else {
            ivRight.setImageResource(R.mipmap.switch_list)
            mBinding.listRecyclerView.layoutManager = mGridManager
            mAdapter = GoodsListAdapter(R.layout.item_goods_common, mGoodsList)
            true
        }
        mBinding.listRecyclerView.adapter = mAdapter
        setRecyclerViewListener()
        resetAdapter(mAdapter)
    }

    private fun setRecyclerViewListener() {
        mAdapter.setOnItemClickListener { adapter, _, position ->
            GoodsDetailActivity.launch(
                requireActivity(),
                (adapter.getItem(position) as GoodsEntity).goodsName
            )
        }
        mAdapter.setOnItemChildClickListener { _, view, _ ->
            when (view.id) {
                R.id.tvShop -> Toast.makeText(requireActivity(), "店铺", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initFilter() {
        mAttrFilterAdapter = GoodsAttrFilterAdapter(null)
        mBinding.includeFilter.hRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.includeFilter.hRecyclerView.adapter = mAttrFilterAdapter
        mAttrFilterAdapter.setOnItemClickListener { adapter, view, position ->
            val popWindow = PopupWindowList(requireActivity())
            val goodsAttrFilter = adapter.data[position] as GoodsAttrFilterEntity
            if (goodsAttrFilter.subAttrList != null && goodsAttrFilter.subAttrList!!.isNotEmpty()) {
                popWindow.setData(goodsAttrFilter.subAttrList!!)
                popWindow.showAsDropDown(view)
                popWindow.applyDim(0.5f, mBinding.listRecyclerView)
                goodsAttrFilter.isSelected = true
                popWindow.setOnPopupListener(object : PopupWindowList.OnPopupListener {
                    override fun onDismiss(isConfirm: Boolean) {
                        if (!isConfirm) {
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

                    override fun onConfirm(selects: List<GoodsAttrFilterEntity>) {
                        popWindow.dismiss(isConfirm = true)
                        mGoodsList?.clear()
                        goodsAttrFilter.selectString = ""
                        selects.forEach {
                            goodsAttrFilter.selectString += it.attrName + ","
                        }
                        adapter.notifyDataSetChanged()
                        mViewModel.loadSeckillGoodsData()
                    }
                })
            } else {
                goodsAttrFilter.isSelected = !goodsAttrFilter.isSelected
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun initObserve() {
        mViewModel.mSearchKey.observe(viewLifecycleOwner, {
            // 调父类的 initRequestData 只加载列表数据
            super.initRequestData()
        })
    }

    override fun initRequestData() {
        super.initRequestData()

        mViewModel.loadFilterAttrsData().observe(viewLifecycleOwner,{
            mAttrFilterAdapter.setNewData(it.toMutableList())
        })
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        Handler().postDelayed({
            mViewModel.loadGoodsList(pageSize, page).observe(this,{
                if (action != ACTION_MORE) {
                    mGoodsList = it.toMutableList()
                } else {
                    mGoodsList?.addAll(it.toMutableList())
                }
                loadCompleted(action, list = it)
            })
        }, 2000)
    }

}