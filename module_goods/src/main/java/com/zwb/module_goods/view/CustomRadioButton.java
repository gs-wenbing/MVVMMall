package com.zwb.module_goods.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

@SuppressLint("AppCompatCustomView")
public class CustomRadioButton extends RadioButton {
    private static final String TAG = CustomRadioButton.class.getSimpleName();

    public CustomRadioButton(Context context) {
        super(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int gravity = getGravity();
        Drawable[] drawables = getCompoundDrawables();
        if(drawables[2]!=null && gravity == Gravity.CENTER){
            float textWidth =getPaint().measureText(getText().toString());
            int drawablePadding =getCompoundDrawablePadding();
            float bodyWidth = textWidth + drawables[2].getIntrinsicWidth() + drawablePadding;
            setPadding(0, 0, (int) (getWidth() - bodyWidth),0);
            canvas.translate((getWidth() - bodyWidth) / 2,0);
        }

        super.onDraw(canvas);
    }
}