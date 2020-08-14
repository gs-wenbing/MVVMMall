package com.zwb.mvvm_mall.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.zwb.mvvm_mall.ui.home.view.CategoryView
import com.zwb.mvvm_mall.common.view.nested.tab.DynamicTabBean
import com.zwb.mvvm_mall.common.view.nested.tab.DynamicTabLayout

class CategoryPagerAdapter(
    private val viewList: ArrayList<CategoryView>,
    private val tabTitleList: ArrayList<String>
) : PagerAdapter(), DynamicTabLayout.DynamicTabProvider {

    private var mCurrentPrimaryItem: CategoryView? = null

    override fun getCount(): Int {
        return viewList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = viewList[position]
        if (container == view.parent) {
            container.removeView(view)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //container.removeView((View) object);
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        val item = obj as CategoryView
        if(item != mCurrentPrimaryItem) {
            mCurrentPrimaryItem?.onUserVisibleChange(false)
        }
        item.onUserVisibleChange(true)
        mCurrentPrimaryItem = item
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleList[position]
    }

    override fun getPageTitleItem(position: Int): DynamicTabBean? {
        return DynamicTabBean(tabTitleList[position], "精品推荐2")
    }
}