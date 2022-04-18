package com.zwb.lib_common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zwb.lib_common.R


class HIndicators @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBgRect = RectF()
    private var mRadius = 0f
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRect = RectF()
    private var viewWidth = 0
    private var mBgColor: Int = Color.parseColor("#CBCFD7")
    private var mIndicatorColor: Int = Color.parseColor("#ff4646")
    var ratio = 0.5f //长度比例
    var progress = 0f //滑动进度比例

    init {
        @SuppressLint("CustomViewStyleable")
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.indicator)
        mBgColor = typedArray.getColor(R.styleable.indicator_normalColor, mBgColor)
        mIndicatorColor = typedArray.getColor(R.styleable.indicator_indicatorColor, mIndicatorColor)
        typedArray.recycle()
        mBgPaint.color = mBgColor
        mBgPaint.style = Paint.Style.FILL
        mPaint.color = mIndicatorColor
        mPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        mBgRect[0f, 0f, w * 1f] = h * 1f
        mRadius = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制背景
        canvas.drawRoundRect(mBgRect, mRadius, mRadius, mBgPaint)

        //计算指示器的长度和位置
        val leftOffset = viewWidth * (1f - ratio) * progress
        val left = mBgRect.left + leftOffset
        val right = left + viewWidth * ratio
        mRect[left, mBgRect.top, right] = mBgRect.bottom

        //绘制指示器
        canvas.drawRoundRect(mRect, mRadius, mRadius, mPaint)
    }

    fun set(value: Float) {
        ratio = value
        invalidate()
    }

    fun setProgress1(value: Float) {
        progress = value
        invalidate()
    }
    /**
     * 设置指示器背景进度条的颜色
     * @param color 背景色
     */
    fun setBgColor(color: Int) {
        mBgPaint.color = color
        invalidate()
    }

    /**
     * 设置指示器的颜色
     * @param color 指示器颜色
     */
    fun setIndicatorColor(color: Int) {
        mPaint.color = color
        invalidate()
    }

    /**
     * 绑定recyclerView
     */
    fun bindRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offsetX = recyclerView.computeHorizontalScrollOffset().toFloat()
                val range = recyclerView.computeHorizontalScrollRange().toFloat()
                val extend = recyclerView.computeHorizontalScrollExtent().toFloat()
                val progres = offsetX * 1.0f / (range - extend)
                progress = progres //设置滚动距离所占比例
                setProgress1(progress)
            }
        })
    }
}