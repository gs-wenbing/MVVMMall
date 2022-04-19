package com.zwb.module_home.view

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.utils.BarUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_home.databinding.HomeFragmentHomeBinding
import com.zwb.module_home.view.recyclerView.ParentRecyclerView

/**
 * 首页滑动帮助类
 */
class SyncScrollHelper(binding: HomeFragmentHomeBinding) {

    private val statusBarHeight = BarUtils.getStatusBarHeight()
    private val toolbarHeight = UIUtils.dp2px(50f).toFloat()
    private var screenHeight = UIUtils.getScreenWidth()//activity.getScreenHeight()
    private var searchBarHeight = UIUtils.dp2px(46f).toFloat()

    private val toolBarLayout = binding.mainToolbar
    private val searchBarLayout = binding.mainSearchLayout
    private val backIv1 = binding.mainBackImg1
    private val backIv2 = binding.mainBackImg2

    private val logoImageView = binding.mainTopLogo

    private val floatAdLayout = binding.homeFloatLayout
    private var floatAdClosed = false

    companion object {
        private const val BACK_DIMENSION_RATIO1 = 1.8125f
        private const val BACK_DIMENSION_RATIO2 = 0.992647f
    }

    init {
        val recyclerView = binding.mainRecyclerView
        val stickyHeight = UIUtils.dp2px(10f)
        recyclerView.setStickyHeight(stickyHeight)

        binding.homeFloatCloseBtn.setOnClickListener {
            floatAdClosed = true
            floatAdLayout.visibility = View.GONE
            recyclerView.setStickyHeight(UIUtils.dp2px(-40f))
        }
    }

    fun initLayout() {
        val backImgHeight1 = screenHeight / BACK_DIMENSION_RATIO1
        val translationY1 = backImgHeight1 - statusBarHeight - toolbarHeight - searchBarHeight
        backIv1.translationY = -translationY1

        val backImgHeight2 = screenHeight / BACK_DIMENSION_RATIO2
        val translationY2 = backImgHeight2 - backImgHeight1
        backIv2.translationY = -translationY2

        searchBarLayout.translationY = statusBarHeight + toolbarHeight

    }
    var temp = 0f
    /**
     * RecyclerView滑动时，动态更新logoImageView和searchBar
     */
    fun syncRecyclerViewScroll(recyclerView: ParentRecyclerView) {
        // 1. Scroll滑动时顶部联动处理
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val scrollY = recyclerView.computeVerticalScrollOffset()
                val minTranslationY = statusBarHeight + UIUtils.dp2px(9f).toFloat()
                val maxTranslationY = statusBarHeight + toolbarHeight
                val targetTranslationY = maxTranslationY - scrollY * 0.7f

                val backImgHeight1 = screenHeight / BACK_DIMENSION_RATIO1
                val backImgHeight2 = screenHeight / BACK_DIMENSION_RATIO2
                val translationY2 = backImgHeight2 - backImgHeight1

                backIv2.translationY = -translationY2 - scrollY
                val translationY1 = backImgHeight1 - statusBarHeight - toolbarHeight - searchBarHeight
                backIv1.translationY = -translationY1 - scrollY

                // 1. logo的alpha处理
                var alpha = 1 - scrollY / 2 / (maxTranslationY - minTranslationY)
                if (alpha < 0) {
                    alpha = 0f
                }
                logoImageView.alpha = alpha

                // 2. 搜索框位移调整
                searchBarLayout.translationY = if (targetTranslationY < minTranslationY) {
                    minTranslationY
                } else {
                    targetTranslationY
                }

                // 3. 搜索框大小调整
                val maxMarginRight = UIUtils.dp2px(92f)
                var progress = (1 - alpha) * 2f
                if (progress > 1) {
                    progress = 1.0f
                }
                val layoutParams = searchBarLayout.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.setMargins(0, 0, (maxMarginRight * progress).toInt(), 0)
                searchBarLayout.layoutParams = layoutParams
            }
        })

        // 2. 广告悬浮位（吸顶）
        recyclerView.setStickyListener {
            if (!floatAdClosed) {
                floatAdLayout.visibility = when {
                    it -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }

    /**
     * 已经置顶时，列表继续下拉的处理
     */
    fun syncRefreshPullDown(refreshLayout: SmartRefreshLayout) {
        val purposeListener = object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                // 监听refreshLayout位置变动
                val backImgHeight1 = screenHeight / BACK_DIMENSION_RATIO1
                val backImgHeight2 = screenHeight / BACK_DIMENSION_RATIO2
                val maxTranslationY =
                    backImgHeight1 - statusBarHeight - toolbarHeight - searchBarHeight
                if (offset > maxTranslationY) {
                    val outOfOffset = offset - maxTranslationY

                    backIv1.alpha = 0f
                    toolBarLayout.alpha = 0f
                    searchBarLayout.alpha = 0f

                    backIv1.translationY = 0f

                    val translationY2 = backImgHeight2 - backImgHeight1 - outOfOffset
                    backIv2.translationY = -translationY2
                } else {
                    val alpha = (maxTranslationY - offset) / maxTranslationY
                    backIv1.alpha = alpha
                    toolBarLayout.alpha = alpha
                    searchBarLayout.alpha = alpha

                    val translationY1 = maxTranslationY - offset
                    backIv1.translationY = -translationY1

                    val translationY2 = backImgHeight2 - backImgHeight1
                    backIv2.translationY = -translationY2
                }
            }
        }
        val h = (UIUtils.getScreenHeight() - statusBarHeight - toolbarHeight - searchBarHeight-20).toInt()
        refreshLayout.height(h)
        refreshLayout.setOnMultiPurposeListener(purposeListener)
    }
}