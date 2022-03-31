package com.zwb.mvvm_mall.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.common.utils.UIUtils

/**
 * ViewPager2的指示器
 */
class ViewPagerIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var viewPager2: ViewPager2? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var largerWidth = 10
    private var smallerWidth = 10
    private var rectHeight = 4
    private var interval = 4
    private var dotsCount = 0

    private var selectedColor: Int = ContextCompat.getColor(context,R.color.mainRed)
    private var normalColor = Color.parseColor("#afdddddd")

    init {
        paint.style = Paint.Style.FILL
        largerWidth = UIUtils.dp2px(12f)
        smallerWidth = UIUtils.dp2px(7f)
        rectHeight = UIUtils.dp2px(2f)
        interval = UIUtils.dp2px(4f)

        val attr =
            context.theme.obtainStyledAttributes(attrs, R.styleable.indicator, defStyleAttr, 0)
        normalColor = attr.getColor(R.styleable.indicator_normalColor, normalColor)
        selectedColor = attr.getColor(R.styleable.indicator_selectedColor, selectedColor)
        attr.recycle()
    }

    fun setViewPager2(viewPager2: ViewPager2, realCount: Int) {
        this.dotsCount = realCount
        this.viewPager2 = viewPager2;
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                invalidate()
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (dotsCount > 1) {
            val width = (smallerWidth + interval) * (dotsCount - 1) + largerWidth
            val height = rectHeight + 1
            setMeasuredDimension(width.toInt(), height.toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val adapter = viewPager2?.adapter
        if (adapter !== null) {
            var startX = 0f
            for (index in 0..dotsCount) {
                val realIndex = viewPager2!!.currentItem % dotsCount
                var thisWidth = if (index == realIndex) {
                    paint.color = selectedColor
                    largerWidth
                } else {
                    paint.color = normalColor
                    smallerWidth
                }

                val fromX = startX
                val fromY = 0f
                val toX = startX + thisWidth
                val toY = rectHeight

                canvas.drawRoundRect(fromX, fromY, toX,
                    toY.toFloat(), (rectHeight / 2).toFloat(), (rectHeight / 2).toFloat(), paint)
                startX = toX + interval
            }
        }
    }
}