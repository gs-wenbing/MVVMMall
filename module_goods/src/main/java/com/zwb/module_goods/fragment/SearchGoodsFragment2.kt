//package com.zwb.module_goods.fragment
//
//import android.annotation.SuppressLint
//import android.os.Handler
//import android.view.View
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.fragment.app.activityViewModels
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.zwb.lib_base.mvvm.v.BaseFragment
//import com.zwb.lib_common.bean.GoodsEntity
//import com.zwb.module_goods.GoodsViewModel
//import com.zwb.module_goods.R
//import com.zwb.module_goods.activity.GoodsDetailActivity
//import com.zwb.module_goods.adapter.GoodsAttrFilterAdapter
//import com.zwb.module_goods.adapter.GoodsListAdapter
//import com.zwb.module_goods.bean.GoodsAttrFilterEntity
//import com.zwb.module_goods.databinding.FragmentSearchGoodsBinding
//import com.zwb.module_goods.view.PopupWindowList
//import kotlinx.android.synthetic.main.fragment_search_goods.*
//import kotlinx.android.synthetic.main.layout_goodslist_filter.*
//
//class SearchGoodsFragment2 : BaseFragment<FragmentSearchGoodsBinding,GoodsViewModel>() {
//    override val mViewModel by activityViewModels<GoodsViewModel>()
//
//    private lateinit var mAdapter: GoodsListAdapter
//    private lateinit var mAttrFilterAdapter: GoodsAttrFilterAdapter
//    private lateinit var mGridManager: GridLayoutManager
//    private  lateinit var mLinearManager: LinearLayoutManager
//    var mGoodsList:MutableList<GoodsEntity>?=null
//    private var mToGrid = true
//    private var isLoadMore = true
//
//
//    override fun FragmentSearchGoodsBinding.initView() {
//        initAdapter()
//        initFilter()
//        listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            @SuppressLint("RestrictedApi")
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (recyclerView.canScrollVertically(-1)) {
//                    actionButton.visibility = View.VISIBLE
//                } else {
//                    //滑动到顶部
//                    actionButton.visibility = View.INVISIBLE
//                }
//            }
//        })
//        actionButton.setOnClickListener {
//            listRecyclerView.scrollToPosition(0)
//        }
//    }
//
//    override fun initObserve() {
//        mViewModel.mSeckillGoods.observe(viewLifecycleOwner, {
//            if (!isLoadMore) {
//                mGoodsList = it.toMutableList()
//            } else {
//                mGoodsList?.addAll(it.toMutableList())
//            }
//            mAdapter.setNewData(mGoodsList)
//        })
//        mViewModel.mFilterAttrs.observe(viewLifecycleOwner, {
//            mAttrFilterAdapter.setNewData(it.toMutableList())
//        })
//
//        mViewModel.mSearchKey.observe(viewLifecycleOwner, {
//            initRequestData()
//        })
//    }
//
//    override fun initRequestData() {
//        isLoadMore = false
////        showPlaceHolder()
//        mViewModel.loadFilterAttrsData()
//        mViewModel.loadSeckillGoodsData()
//    }
//
//    private fun initFilter(){
//        mAttrFilterAdapter = GoodsAttrFilterAdapter(null)
//        hRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
//        hRecyclerView.adapter = mAttrFilterAdapter
//        mAttrFilterAdapter.setOnItemClickListener { adapter, view, position ->
//            val popWindow = PopupWindowList(requireActivity())
//            val goodsAttrFilter = adapter.data[position] as GoodsAttrFilterEntity
//            if(goodsAttrFilter.subAttrList!=null && goodsAttrFilter.subAttrList!!.isNotEmpty()){
//                popWindow.setData(goodsAttrFilter.subAttrList!!)
//                popWindow.showAsDropDown(view)
//                popWindow.applyDim(0.5f,listRecyclerView)
//                goodsAttrFilter.isSelected = true
//                popWindow.setOnPopupListener(object : PopupWindowList.OnPopupListener{
//                    override fun onDismiss(isConfirm: Boolean) {
//                        if(!isConfirm){
//                            popWindow.updateItem(isConfirm = false)
//                            goodsAttrFilter.isSelected = false
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//
//                    override fun onReset() {
//                        popWindow.updateItem(isConfirm = false)
//                        goodsAttrFilter.selectString = ""
//                        adapter.notifyDataSetChanged()
//                    }
//
//                    override fun onConfirm(selects:List<GoodsAttrFilterEntity>) {
//                        popWindow.dismiss(isConfirm = true)
//                        mGoodsList?.clear()
//                        goodsAttrFilter.selectString = ""
//                        selects.forEach {
//                            goodsAttrFilter.selectString += it.attrName+","
//                        }
//                        adapter.notifyDataSetChanged()
//                        isLoadMore = false
//                        mViewModel.loadSeckillGoodsData()
//                    }
//                })
//            }else{
//                goodsAttrFilter.isSelected = !goodsAttrFilter.isSelected
//                adapter.notifyDataSetChanged()
//            }
//        }
//    }
//
//    private fun initAdapter(){
//        mAdapter = GoodsListAdapter(R.layout.item_goods_linear_layout,null)
//        mLinearManager = LinearLayoutManager(requireActivity())
//        mGridManager = GridLayoutManager(requireActivity(), 2)
//        listRecyclerView.layoutManager = mLinearManager
//        listRecyclerView.adapter = mAdapter
//        setRecyclerViewListener()
//    }
//    fun switchList(ivRight: ImageView){
//        mToGrid = if(mToGrid){
//            ivRight.setImageResource(R.mipmap.switch_grid)
//            listRecyclerView.layoutManager = mLinearManager
//            mAdapter = GoodsListAdapter(R.layout.item_goods_linear_layout,mGoodsList)
//            false
//        }else{
//            ivRight.setImageResource(R.mipmap.switch_list)
//            listRecyclerView.layoutManager = mGridManager
//            mAdapter = GoodsListAdapter(R.layout.item_goods_common,mGoodsList)
//            true
//        }
//        listRecyclerView.adapter = mAdapter
//        setRecyclerViewListener()
//    }
//
//    private fun setRecyclerViewListener(){
//        mAdapter.setOnItemClickListener { adapter, _, position ->
//            GoodsDetailActivity.launch(requireActivity(), (adapter.getItem(position) as GoodsEntity).goodsName)
//        }
//        mAdapter.setOnItemChildClickListener { _, view, _ ->
//            when (view.id) {
//                R.id.tvShop -> Toast.makeText(requireActivity(),"店铺", Toast.LENGTH_SHORT).show()
//            }
//        }
//        mAdapter.setOnLoadMoreListener({
//            Handler().postDelayed({
//                isLoadMore = true
//                mViewModel.loadSeckillGoodsData()
//            }, 2000)
//        },listRecyclerView)
//    }
//}