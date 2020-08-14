package com.zwb.mvvm_mall.common.view.nested

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zwb.mvvm_mall.ui.home.view.ParentRecyclerView

class StoreSwipeRefreshLayout : SwipeRefreshLayout {

    private var mParentRecyclerView: ParentRecyclerView? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mParentRecyclerView == null) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child is ParentRecyclerView) {
                    mParentRecyclerView = child
                    break
                }
            }
        }

    }

    override fun canChildScrollUp(): Boolean {
        return super.canChildScrollUp() || mParentRecyclerView?.isChildRecyclerViewCanScrollUp()?:false
    }
}