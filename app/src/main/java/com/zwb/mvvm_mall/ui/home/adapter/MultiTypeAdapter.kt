package com.zwb.mvvm_mall.ui.home.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.ui.home.view.CategoryView
import com.zwb.mvvm_mall.ui.home.view.ChildRecyclerView
import com.zwb.mvvm_mall.common.view.nested.bean.CategoryBean
import com.zwb.mvvm_mall.common.view.nested.tab.DynamicTabLayout

class MultiTypeAdapter(dataSet:ArrayList<CategoryBean>, private var listener: ChildRecyclerView.OnRecyclerViewLoadListener)
    : BaseQuickAdapter<CategoryBean, BaseViewHolder>(R.layout.layout_item_category,dataSet) {

    private lateinit var mTabLayout: DynamicTabLayout;
    private lateinit var mViewPager: ViewPager;

    val viewList = ArrayList<CategoryView>()
    private var cacheVies = HashMap<String, CategoryView>()

    private var mCurrentRecyclerView : ChildRecyclerView? = null

    private var isTabExpanded = true



    override fun convert(helper: BaseViewHolder, item: CategoryBean?) {
        mTabLayout = helper.getView<DynamicTabLayout>(R.id.newTabLayout)
        mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)

        mViewPager = helper.getView<ViewPager>(R.id.viewPager)
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if(viewList.isEmpty().not()) {
                    mCurrentRecyclerView = viewList[position]
                    mCurrentRecyclerView?.apply {
                        addOnScrollListener(object :RecyclerView.OnScrollListener(){
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                if(dy != 0) {
                                    dealWithChildScrollEvents(this@apply.isScrollTop())
                                }
                            }
                        })
                    }
                }
            }
            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        item.apply {
            viewList.clear()
            if(cacheVies.size > this!!.tabTitleList.size) {
                cacheVies.clear()
            }
            for ((index, value) in tabTitleList.withIndex()) {
                var categoryView = cacheVies[value]
                if(categoryView == null || categoryView.parent != mViewPager) {
                    categoryView = CategoryView(mContext,index = index)
                    categoryView.setOnRecyclerViewLoadListener(listener)
                    cacheVies[value] = categoryView
                }
                viewList.add(categoryView)
            }
            mCurrentRecyclerView = viewList[mViewPager.currentItem]
            val lastItem = mViewPager.currentItem

            mViewPager.adapter = CategoryPagerAdapter(viewList, tabTitleList)
            mTabLayout.setupWithViewPager(mViewPager)
            mViewPager.currentItem = lastItem
            //默认bind第一个子RecyclerView的滑动，不然第一个tab不会执行动画
            bindDefaultChildRecyclerViewScrolling(viewList[0])
        }
    }

    private fun bindDefaultChildRecyclerViewScrolling(categoryView: CategoryView) {
        categoryView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy != 0) {
                        dealWithChildScrollEvents(this@apply.isScrollTop())
                    }
                }
            })
        }
    }


    private fun dealWithChildScrollEvents(scrollTop: Boolean) {
        if(isTabExpanded.not() && scrollTop) {
            mTabLayout.changeDescHeightWithAnimation(false)
            mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
            isTabExpanded = true
        } else if(isTabExpanded && scrollTop.not()) {
            mTabLayout.changeDescHeightWithAnimation(true)
            mTabLayout.setSelectedTabIndicatorColor(Color.RED)
            isTabExpanded = false
        }

    }

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        return mCurrentRecyclerView
    }
    fun getRecyclerViewList():HashMap<String, CategoryView>?{
        return cacheVies
    }
    fun destroy() {
        cacheVies.clear()
    }



}