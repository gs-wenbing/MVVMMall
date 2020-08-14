package com.zwb.mvvm_mall.common.view

import android.animation.*
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.DecelerateInterpolator
import kotlin.math.abs

class ScrollContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    val TAG = "fuxiao"

    private var mDownX  = 0// 手指按下的x轴位置
    private var mDownY = 0 // 手指按下的y轴位置

    private var mLastOffsetY = 0 // 上次坐标

    /**
     * first 指头一部分view
     * second 指第二部分view
     * barView 指titleBar
     */

    /** 移动距离 */
    private var firstTranslateY = 0f
    private var secondTranslateY = 0f
    private var barViewTranslateY = 0f

    /** 开始动画时的起止位置*/
    private var firstStartY = 0f
    private var firstEndY = 0f

    private var secondStartY = 0f
    private var secondEndY = 0f

    /** 可以滑动的最大距离*/
    private var secondCanMaxUp = 0f
    private var secondCanMaxDown = 0
    private var firstCanMaxUp = 0f
    private var barViewCanMaxDown = 0

    /** 回弹底部顶部的分界线*/
    private var boundaryLine = 0

    /** 是否正在执行震动动画*/
    private var isBombing = false

    /** 是否先下滑然后上滑*/
    private var isDownFirstThenUp = false

    /** 所有动画取消*/
    private var isAllAnimCancel = false

    /** alpha动画控制*/
    private var isScrollDownDirectly = false

    private lateinit var mFirstChild: View

    private lateinit var mSecondChild: View

    private lateinit var mTitleBarView: View

    private lateinit var mVelocityTracker : VelocityTracker// 计算滑动速度
    private lateinit var mSecondAnimator : ValueAnimator// 动画
    private lateinit var mSecondFlingAnimator : ValueAnimator// 模拟Fling动画
    private lateinit var mShakeAnimator: ObjectAnimator // 模拟震动动画

    private lateinit var mAlphaAnimator: ObjectAnimator // First alpha动画

    private var animatorSet = AnimatorSet()

    //自定义手势监听类
    private var mTouchLisener: ScrollTouchListener? = null
    //手势监听
    private var mDetector: GestureDetector? = null

    init {
        initView()
    }

    /*
     *测量方法，测量父布局的宽度和高度
     */
    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) { //重新设置宽高
        setMeasuredDimension(
            measureWidth(widthMeasureSpec, heightMeasureSpec),
            measureHeight(widthMeasureSpec, heightMeasureSpec)
        )
    }

    /**
     * 测量宽度
     */
    private fun measureWidth(widthMeasureSpec: Int, heightMeasureSpec: Int): Int { // 宽度
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }
        return if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else sizeWidth
    }

    /**
     * 测量高度
     */
    private fun measureHeight(widthMeasureSpec: Int, heightMeasureSpec: Int): Int { //高度
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        var height = 0
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childHeight = child.measuredHeight
            height += childHeight
        }
        return if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height
    }

    /**
     * 给子布局设定位置
     */
    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        var childTop = 0 //子View顶部的间距
        val childWidth = width //子View的宽度
        var height: Int //子view的高度
        val childCount = childCount //子View的数量
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            height = child.measuredHeight
            child.layout(
                0,
                childTop + lp.topMargin,
                childWidth,
                childTop + height + lp.bottomMargin
            )
            childTop += height
        }
    }

    /** 将顶部的TitleBarView设置进来
     *  要跟着secondView移动变化
     */
    public fun setTitleBarView(view: View) {
        mTitleBarView = view
    }

    fun initView() {
        //手势指示器初始化
        mTouchLisener = ScrollTouchListener()
        mDetector = GestureDetector(context, mTouchLisener)

        mVelocityTracker = VelocityTracker.obtain()

        mSecondAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(1000)
        mSecondAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            Log.e(TAG,"second执行 $value transY:${mSecondChild.translationY}")
            if (isDownFirstThenUp)
                translateFirst(firstStartY + (value * (firstEndY - firstStartY)))
            translateSecond(secondStartY + (value * (secondEndY - secondStartY)) - mSecondChild.translationY)
        })
        mSecondAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {

            }
        })

        mSecondFlingAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(200)
        mSecondFlingAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            Log.e(TAG,"fling执行$value transY:${mSecondChild.translationY}")
            translateSecond(secondStartY + (value * (secondEndY - secondStartY)) - mSecondChild.translationY)
        })
        mSecondFlingAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.e(TAG,"fling 完成 $firstTranslateY")
                if (isAllAnimCancel) {
                    Log.e(TAG,"********")
                    return
                }
                if ( secondTranslateY > -boundaryLine) {
                    // 向上滑，未超过分界线抬起,到底部
                    setPivotStart()
                    mSecondAnimator.interpolator = DecelerateInterpolator(3f)
                    mSecondAnimator.start()
                } else if (secondTranslateY < -boundaryLine && abs(secondTranslateY) < abs(secondCanMaxUp)) {
                    // 向上滑超过分界线抬起，到顶部
                    setPivotEnd()
                    mSecondAnimator.interpolator = DecelerateInterpolator(2f)
                    mSecondAnimator.start()
                }
            }
        })
    }

    /**
     * 获得 First 和 Second 子View
     * 获得可移动的最大距离，根据xml中值设定
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e(TAG, "onAttach")
        mFirstChild = getChildAt(0)
        mSecondChild = getChildAt(1)
        mSecondChild.post {
            secondCanMaxDown = dp2px(context, 60)
            secondCanMaxUp =
                mSecondChild.height - measuredHeight - mSecondChild.top.toFloat() - secondCanMaxDown
            firstCanMaxUp = -mFirstChild.height.toFloat() - 20
            boundaryLine = dp2px(context, 130)
            barViewCanMaxDown = dp2px(context,90)
            Log.e(TAG, "最大移动：$secondCanMaxUp")
        }

        mShakeAnimator = ObjectAnimator.ofFloat(mSecondChild,"translationY",0f,-5f)
        mShakeAnimator.duration = 500
        mShakeAnimator.interpolator = CycleInterpolator(2.5f)

        mAlphaAnimator = ObjectAnimator.ofFloat(mFirstChild,"Alpha",0.2f,1f)
        mAlphaAnimator.duration = 800
    }


    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()


        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = x
                mDownY = y
                mLastOffsetY = y
                Log.e(TAG, "按下 $mDownY")
                isBombing = false
                if (mFirstChild.translationY == 0f)
                    isDownFirstThenUp = false
                isAllAnimCancel = true
                if (animatorSet.isRunning){
                    animatorSet.cancel()
                }
                if (mSecondAnimator.isRunning) {
                    mSecondAnimator.cancel()
                }
                if (mSecondFlingAnimator.isRunning) {
                    mSecondFlingAnimator.cancel()
                }
                isAllAnimCancel = false
                Log.e(TAG,"-------")
            }
            MotionEvent.ACTION_MOVE -> {
                if ( 0 < abs(y - mDownY)) {
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector!!.onTouchEvent(event)
        mVelocityTracker.addMovement(event)
        mVelocityTracker.computeCurrentVelocity(10)
        val y = event!!.rawY.toInt()
        val offsetY = y - mLastOffsetY

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                mLastOffsetY = y
                if (offsetY < 0) {
                    isScrollDownDirectly = false
                    // 向上滑动
                    if (mSecondChild.translationY > 0){
                        isDownFirstThenUp = true
                    }
                    translateSecond(offsetY.toFloat() )
                } else if (offsetY > 0) {
                    // 向下滑动
                    translateSecond(offsetY.toFloat() )
                }
                if (offsetY > 0 && overCrossBoundary() &&
                    !mAlphaAnimator.isRunning &&
                    !isScrollDownDirectly &&
                        mTitleBarView.translationY > abs(barViewCanMaxDown) - 30){
                    // alpha正在执行时不执行 && 重复下滑不执行（下滑再上滑后再执行）&& 超过一定高度再执行
                    mAlphaAnimator.start()
                    isScrollDownDirectly = true
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.e(TAG, "抬起：$secondTranslateY")
                // Fling 执行时先执行Fling
                if (mSecondFlingAnimator.isRunning)
                    return true

                if (secondTranslateY > 0) {
                    // 向下滑抬起
                    Log.e(TAG,"下滑动画：$isDownFirstThenUp")
                    isBombing = true
                    setPivotStart()
                    mSecondAnimator.interpolator = AccelerateDecelerateInterpolator()
                    animatorSet.play(mShakeAnimator).after(mSecondAnimator)
                    animatorSet.start()
                } else if (abs(secondTranslateY) < abs(boundaryLine)) {
                    // 向上滑，未超过分界线抬起
                    Log.e(TAG,"回弹动画：")
                    setPivotStart()
                    mSecondAnimator.interpolator = AccelerateDecelerateInterpolator()
                    mSecondAnimator.start()
                } else if (abs(secondTranslateY) > abs(boundaryLine)) {
                    // 向上滑超过分界线抬起
                    Log.e(TAG,"顶部动画：")
                    setPivotEnd()
                    mSecondAnimator.duration = 200
                    mSecondAnimator.interpolator = AccelerateDecelerateInterpolator()
                    mSecondAnimator.start()
                }
            }
        }
        return true
    }

    private fun overCrossBoundary(): Boolean {
        return abs(mSecondChild.translationY) > abs(boundaryLine)
    }

    /**
     * 移动First
     */
    private fun translateFirst(distanceY: Float){
        firstTranslateY = distanceY
        Log.e(TAG,"第一个View: $distanceY")
        if (Math.abs(firstTranslateY) <= Math.abs(firstCanMaxUp)) {
            // 未超过最大距离
            if (distanceY < 0 || mFirstChild.translationY < 0) {
                //上滑 || 已经上滑一段距离
                Log.e(TAG,"执行了")
                mFirstChild.translationY = firstTranslateY
            } else {
                //超过时置0
                mFirstChild.translationY = 0f
            }
        }
    }

    /**
     * 移动Second
     */
    private fun translateSecond(distanceY: Float) {
        // second 移动距离 计算 = 滑动距离 + 当前移动距离
        secondTranslateY = distanceY + mSecondChild.translationY

        // first 移动距离
        firstTranslateY = distanceY * 1.3f + mFirstChild.translationY
        Log.e(TAG,"distanceY: $distanceY")

        // 没超过最大距离 && 不是回弹动画 && (不是先下滑后上滑的情况)
        if (Math.abs(firstTranslateY) <= Math.abs(firstCanMaxUp)
            && !isBombing
            && (!mSecondAnimator.isRunning || !isDownFirstThenUp)) {
            if (distanceY < 0 || firstTranslateY < 0) {
                // 移动first
                mFirstChild.translationY = firstTranslateY
            } else {
                mFirstChild.translationY = 0f
            }
        }
        barViewTranslateY = secondTranslateY * 0.5f
        if (abs(barViewTranslateY) > abs(barViewCanMaxDown)) {
            barViewTranslateY = -barViewCanMaxDown.toFloat()
        } else if (mSecondChild.translationY > 0){
            barViewTranslateY = 0f
        }
        if (secondTranslateY > 0 && secondTranslateY > secondCanMaxDown) {
            // second超过底部
            secondTranslateY = secondCanMaxDown.toFloat()
        }
        if (distanceY < 0 && Math.abs(secondTranslateY) >= Math.abs(secondCanMaxUp)) {
            // second超过顶部
            secondTranslateY = secondCanMaxUp
        }
        val percent = abs(barViewTranslateY) / mTitleBarView.height
        mTitleBarView.alpha = percent
        // 移动barView
        mTitleBarView.translationY = -barViewTranslateY
        // 移动second
        mSecondChild.translationY = secondTranslateY
    }

    /**
     * 设置返回底部位置
     */
    private fun setPivotStart(){
        secondStartY = secondTranslateY
        secondEndY = 0f
        firstStartY = firstTranslateY
        firstEndY = 0f
        mSecondAnimator.duration = 300
    }

    /**
     * 设置回弹顶部位置
     */
    private fun setPivotEnd(){
        secondStartY = secondTranslateY
        secondEndY = secondCanMaxUp
        firstStartY = firstTranslateY
        firstEndY = firstCanMaxUp
        mSecondAnimator.duration = 300
    }
    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    /**
     * 自定义手势监听类
     */
    private inner class ScrollTouchListener : GestureDetector.OnGestureListener {
        //按下事件
        override fun onDown(e: MotionEvent): Boolean {
            return false
        }

        //单击事件
        override fun onShowPress(e: MotionEvent) {
        }

        //手指抬起事件
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return false
        }

        //滚动事件
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.e(TAG, "Y: $distanceY")
            return false
        }

        //长按事件
        override fun onLongPress(e: MotionEvent) {
        }

        //滑动事件
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.e(TAG, "fling: $velocityY")
            if (abs(mVelocityTracker.yVelocity) > 5 && mFirstChild.translationY != 0f)
                simulateFling(velocityY)
            return false
        }
    }

    /**
     * 模拟Fling
     */
    private fun simulateFling(fling: Float) {
        Log.e(TAG, "速度：${mVelocityTracker.yVelocity}")
        secondStartY = mSecondChild.translationY
        if (abs(mVelocityTracker.yVelocity) < 30){
            secondEndY = secondTranslateY + fling / 10
            mSecondFlingAnimator.interpolator = DecelerateInterpolator(2f)
        } else {
            mSecondFlingAnimator.interpolator = DecelerateInterpolator(1f)
            secondEndY = secondTranslateY + fling / 5
        }
        if (secondEndY > 0) {
            secondEndY = 0f
        } else if (secondEndY < secondCanMaxUp) {
            secondEndY = secondCanMaxUp
        }
        mSecondFlingAnimator.duration = getRangeOfDuration(mVelocityTracker.yVelocity)
        mSecondFlingAnimator.start()
        Log.e(TAG,"fling 开始执行")
    }

    /**
     * 不同速率返回时间段
     */
    private fun getRangeOfDuration(velocityY: Float): Long {
        var duration = 0
        val speed = abs(velocityY)
        if (speed > 100) {
            duration = 40
        } else if (speed > 80 && speed < 100) {
            duration = 100
        } else if (speed > 50 && speed < 80) {
            duration = 250
        } else if (speed < 50) {
            duration = 500
        }
        return duration.toLong()
    }

    fun dp2px(context: Context, dpVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal.toFloat(), context.resources.displayMetrics
        ).toInt()
    }
}