package com.zwb.mvvm_mall.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zwb.mvvm_mall.R;
import com.zwb.mvvm_mall.common.utils.UIUtils;

public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆形
        canvas.drawCircle(centreX, radius, radius, mPaint);
        //中间矩形
        canvas.drawRect(centreX-radius,radius,centreX+radius,radius*2+bottomHeight,mPaint);

        canvas.drawRect(0, radius*2, centreX*2, radius*2+bottomHeight, mPaint);

        canvas.drawPath(pathLeft1, mPaint);
        canvas.drawPath(pathRight1, mPaint);
        int bw = bitmap.getWidth();
        canvas.drawBitmap(bitmap , centreX-bw/2 , radius-bw/2 , mPaint);
    }

    /**
     * 1.创建一个画笔
     */
    private Paint mPaint;
    private int radius;
    private int centreX;
    private int bottomHeight;
    Path pathLeft1,pathRight1;
    Bitmap bitmap;

    /**
     * 2.初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        //设置画笔颜色
        mPaint.setColor(Color.parseColor("#E5E5E5"));
        //设置画笔模式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽度为30px
        mPaint.setStrokeWidth(2f);

        radius = UIUtils.dp2px(20f);
        centreX = UIUtils.getScreenWidth()/2;
        bottomHeight = UIUtils.dp2px(5f);

        bitmap = BitmapFactory.decodeResource(getResources() , R.mipmap.input_voice);

        pathLeft1 = new Path();
        pathLeft1.addRect(centreX-radius*2,radius,centreX-radius,radius*2, Path.Direction.CW);
        Path pathLeft2 = new Path();
        RectF ovalLeft = new RectF(centreX-radius*3,0,centreX-radius,radius*2);
        pathLeft2.arcTo(ovalLeft, 0, 180);
        pathLeft1.op(pathLeft2, Path.Op.DIFFERENCE);

        pathRight1 = new Path();
        pathRight1.addRect(centreX+radius,radius,centreX+radius*2,radius*2, Path.Direction.CW);
        Path pathRight2 = new Path();
        RectF ovaRight = new RectF(centreX+radius,0,centreX+radius*3,radius*2);
        pathRight2.arcTo(ovaRight, 0, 180);
        pathRight1.op(pathRight2, Path.Op.DIFFERENCE);

    }
}
