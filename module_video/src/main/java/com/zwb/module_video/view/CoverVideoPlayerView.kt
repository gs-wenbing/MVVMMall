package com.zwb.module_video.view

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.utils.NetInfoModule
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.zwb.module_video.R

class CoverVideoPlayerView : StandardGSYVideoPlayer {
    var mCoverOriginUrl: String? = null
    var mDefaultRes = 0

    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.video_layout_cover
    }

    override fun init(context: Context) {
        super.init(context)
        if (mThumbImageViewLayout != null
            && (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)
        ) {
            mThumbImageViewLayout.visibility = VISIBLE
        }
    }

    /**
     * 设置视频url
     */
    fun setVideoUrl(url: String) {
        mUrl = url
        mOriginUrl = url
    }

    /**
     * 是否边缓存
     */
    fun setVideoCache(cache: Boolean) {
        mCache = cache
    }

    /**
     * 设置title
     */
    fun setVideoTitle(title: String) {
        mTitle = title
    }

    /**
     * 保存播放状态
     */
    fun saveState(): CoverVideoPlayerView {
        val switchVideo = CoverVideoPlayerView(context)
        cloneParams(this, switchVideo)
        return switchVideo
    }

    fun cloneState(coverVideoPlayerView: CoverVideoPlayerView) {
        cloneParams(coverVideoPlayerView, this)
    }



    override fun startWindowFullscreen(
        context: Context,
        actionBar: Boolean, statusBar: Boolean
    ): GSYBaseVideoPlayer {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val sampleCoverVideo = gsyBaseVideoPlayer as CoverVideoPlayerView
        // sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes);
        return gsyBaseVideoPlayer
    }

    override fun showSmallVideo(
        size: Point, actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer {
        // 下面这里替换成你自己的强制转化
        val sampleCoverVideo = super.showSmallVideo(
            size,
            actionBar,
            statusBar
        ) as CoverVideoPlayerView
        sampleCoverVideo.mStartButton.visibility = GONE
        sampleCoverVideo.mStartButton = null
        return sampleCoverVideo
    }

    override fun cloneParams(from: GSYBaseVideoPlayer, to: GSYBaseVideoPlayer) {
        super.cloneParams(from, to)
        val sf = from as CoverVideoPlayerView
        val st = to as CoverVideoPlayerView
        st.mShowFullAnimation = sf.mShowFullAnimation
    }

    /**
     * 退出window层播放全屏效果
     */
    override fun clearFullscreenLayout() {
        if (!mFullAnimEnd) {
            return
        }
        mIfCurrentIsFullscreen = false
        var delay = 0
        if (mOrientationUtils != null) {
            delay = mOrientationUtils.backToProtVideo()
            mOrientationUtils.isEnable = false
            if (mOrientationUtils != null) {
                mOrientationUtils.releaseListener()
                mOrientationUtils = null
            }
        }
        if (!mShowFullAnimation) {
            delay = 0
        }
        val vp = CommonUtil.scanForActivity(context)
            .findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val oldF = vp.findViewById<View>(fullId)
        if (oldF != null) {
            // 此处fix bug#265，推出全屏的时候，虚拟按键问题
            val gsyVideoPlayer = oldF as CoverVideoPlayerView
            gsyVideoPlayer.mIfCurrentIsFullscreen = false
        }
        if (delay == 0) {
            backToNormal()
        } else {
            postDelayed({ backToNormal() }, delay.toLong())
        }
    }

    /******************* 下方两个重载方法，在播放开始前不屏蔽封面，不需要可屏蔽  */
    override fun onSurfaceUpdated(surface: Surface) {
        super.onSurfaceUpdated(surface)
        if (mThumbImageViewLayout != null
            && mThumbImageViewLayout.visibility == VISIBLE
        ) {
            mThumbImageViewLayout.visibility = INVISIBLE
        }
    }

    override fun setViewShowState(view: View, visibility: Int) {
        if (view === mThumbImageViewLayout && visibility != VISIBLE) {
            return
        }
        super.setViewShowState(view, visibility)
    }

    override fun onSurfaceAvailable(surface: Surface) {
        super.onSurfaceAvailable(surface)
        if (GSYVideoType.getRenderType() != GSYVideoType.TEXTURE) {
            if (mThumbImageViewLayout != null
                && mThumbImageViewLayout.visibility == VISIBLE
            ) {
                mThumbImageViewLayout.visibility = INVISIBLE
            }
        }
    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽  */
    protected var byStartedClick = false
    override fun onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE)
            return
        }
        byStartedClick = true
        super.onClickUiToggle()
    }

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        byStartedClick = false
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Debuger.printfLog("Sample changeUiToPreparingShow")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Debuger.printfLog("Sample changeUiToPlayingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun startAfterPrepared() {
        super.startAfterPrepared()
        Debuger.printfLog("Sample startAfterPrepared")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
        setViewShowState(mBottomProgressBar, VISIBLE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        byStartedClick = true
        super.onStartTrackingTouch(seekBar)
    }

    /**
     * 利用反射 解决gsy库中导致的内存泄漏
     */
    fun cancel() {
        mAudioManager.abandonAudioFocus(onAudioFocusChangeListener)
        try {
            if (mNetInfoModule != null) {
                // 拿到NetInfoModule对象中 mConnectivityBroadcastReceiver字段.
                val mConnectivityBroadcastReceiver = NetInfoModule::class.java
                    .getDeclaredField("mConnectivityBroadcastReceiver")
                // 由于是私有字段,所以需要调用setAccessible(true),否则会报错
                mConnectivityBroadcastReceiver.isAccessible = true
                // 根据当前mNetInfoModule对象的 mConnectivityBroadcastReceiver字段值为null
                mConnectivityBroadcastReceiver[mNetInfoModule] = null
                val mNetChangeListener =
                    NetInfoModule::class.java.getDeclaredField("mNetChangeListener")
                mNetChangeListener.isAccessible = true
                mNetChangeListener[mNetInfoModule] = null
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        mAudioManager = null
        mContext = null
    }
}
