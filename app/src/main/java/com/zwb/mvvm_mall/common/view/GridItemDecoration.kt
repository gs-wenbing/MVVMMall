package com.zwb.mvvm_mall.common.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * RecyclerView间距Decoration
 */
class GridItemDecoration(divider: Float,var spanCount:Int = 2) : RecyclerView.ItemDecoration() {

    private val divider = divider.toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        var layoutParams:RecyclerView.LayoutParams?=null
        var spanIndex = 0
        if(view.layoutParams is  StaggeredGridLayoutManager.LayoutParams){
            layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            spanIndex = layoutParams.spanIndex
        }else if(view.layoutParams is  GridLayoutManager.LayoutParams){
            layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            spanIndex = layoutParams.spanIndex
        }
        when {
            spanIndex == 0 -> {
                outRect.left = divider
                outRect.right = divider / spanCount
            }
            spanCount-spanIndex==1 -> {
                outRect.right = divider
                outRect.left = divider / spanCount
            }
            else -> {
                outRect.right = divider / spanCount
                outRect.left = divider / spanCount
            }
        }


        outRect.bottom = divider
        outRect.top = if (layoutParams!!.viewAdapterPosition < spanCount) {
            divider
        } else {
            0
        }

    }
}