package com.zwb.mvvm_mall.ui.classify.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zwb.mvvm_mall.common.utils.UIUtils

class SpaceItemDecoration( private var space:Int) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) %3 == 0) {
            outRect.left = 0 //第一列左边贴边
        } else {
            if (parent.getChildAdapterPosition(view) %3 == 1) {
                outRect.left = space//第二列移动一个位移间距
            } else {
                outRect.left = space * 2//由于第二列已经移动了一个间距，所以第三列要移动两个位移间距就能右边贴边，且item间距相等
            }
        }

        if (parent.getChildAdapterPosition(view) >= 3) {
            outRect.top = UIUtils.dp2px(10f)
        } else {
            outRect.top = 0
        }
    }
}