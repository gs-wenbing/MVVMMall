package com.zwb.mvvm_mall.common.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ImageView
import com.zwb.mvvm_mall.common.utils.UIUtils

class ProgressImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    var drawable = CircularProgressDrawable(context)

    init {
        drawable.strokeWidth = UIUtils.dp2px(2f).toFloat()
        drawable.strokeCap = Paint.Cap.ROUND
        drawable.arrowEnabled = true
        drawable.setColorSchemeColors(Color.WHITE)
        setImageDrawable(drawable)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        drawable.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        drawable.stop()
    }
}