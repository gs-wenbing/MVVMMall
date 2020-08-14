package com.zwb.mvvm_mall.common.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class MyNestedScrollView extends NestedScrollView {
    float startScrollY = 400f;
    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    View mCanMoveView;
    public void setCanMoveView(View view){
        mCanMoveView = view;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        if (ev.getAction()== MotionEvent.ACTION_DOWN){
//            float offset = ev.getY() - startScrollY;
//            if(offset>0){
//                mCanMoveView.scrollBy(0,(int)offset);
//            }
//        }else if(ev.getAction()== MotionEvent.ACTION_UP){
//            float offset = ev.getY() - startScrollY;
//            if(offset>0){
//                mCanMoveView.scrollBy(0,-(int)offset);
//            }
//
//        }
        return super.onTouchEvent(ev);
    }
}
