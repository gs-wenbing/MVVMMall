package com.zwb.mvvm_mall.ui.search.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMActivity
import com.zwb.mvvm_mall.bean.SearchTagEntity
import com.zwb.mvvm_mall.ui.search.adapter.SearchAdapter
import com.zwb.mvvm_mall.ui.search.viewmodel.SearchViewModel
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.common.utils.UIUtils
import com.zwb.mvvm_mall.ui.goods.view.GoodsDetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_home_toolbar.toolbar
import kotlinx.android.synthetic.main.layout_search_header.view.*
import kotlinx.android.synthetic.main.layout_search_toolbar.*

@SuppressLint("Registered")
class SearchActivity :BaseVMActivity<SearchViewModel>(){

    private lateinit var mAdapter:SearchAdapter

    private lateinit var mHeaderView: View

    override val layoutId = R.layout.activity_search

    override fun initView() {
        super.initView()
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.darkMode(this)
        tvSearch.setOnClickListener { GoodsDetailActivity.launch(this,"商品名称") }
        ivBack.setOnClickListener { finish() }

        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_search_header, null)

        mAdapter = SearchAdapter(null)
        rvSearch.layoutManager = LinearLayoutManager(this)
        rvSearch.adapter = mAdapter
        mAdapter.addHeaderView(mHeaderView)
    }

    override fun initData() {
        super.initData()
        mViewModel.loadSearchTags()
        mViewModel.loadSearchHotData()
    }

    override fun initDataObserver() {
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
        list.forEach {
            val tv = TextView(this)
            tv.setBackgroundResource(R.drawable.shape_grey_background)
            tv.setTextColor(getColor(R.color.grey_text))
            tv.setPadding(
                UIUtils.dp2px(15f), UIUtils.dp2px(5f),
                UIUtils.dp2px(15f), UIUtils.dp2px(5f)
            )
            tv.textSize = 14f
            tv.maxLines = 1
            tv.gravity = Gravity.CENTER
            tv.text = it.tagName
            flowLayout.addView(tv)
        }
    }

    companion object{
        fun launch(activity: FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, SearchActivity::class.java))
            }
    }
}