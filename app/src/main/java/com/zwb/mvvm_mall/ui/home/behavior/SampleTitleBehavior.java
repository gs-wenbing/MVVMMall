package com.zwb.mvvm_mall.ui.home.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;
    public SampleTitleBehavior(){

    }

    public SampleTitleBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof RelativeLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        if(deltaY == 0){
            deltaY = dependency.getY()-child.getHeight();
        }

//        float dy1 = dependency.getY() - child.getHeight();
//        dy1 = dy1 < 0 ? 0 : dy1;
//        float y = -(dy1 / deltaY) * child.getHeight();
//        child.setTranslationY(y);

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float alpha = 1 - (dy / deltaY);
        Log.e("------>",alpha+"");
        child.setAlpha(alpha);

        return true;
    }
}
