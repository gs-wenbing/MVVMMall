package com.zwb.module_video.view

import android.view.View
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils

object VideoPlayerHelper {
    private var mVideoView: CoverVideoPlayerView? = null
    private var sMediaPlayerListener: GSYMediaPlayerListener? = null

    /**
     * 播放前初始化配置
     */
    fun optionPlayer(
        gsyVideoPlayer: CoverVideoPlayerView,
        url: String,
        cache: Boolean,
        title: String,
        orientationUtils: OrientationUtils?
    ) {
        //增加title
        gsyVideoPlayer.titleTextView.visibility = View.GONE
        //设置返回键
        gsyVideoPlayer.backButton.visibility = View.VISIBLE
        //设置全屏按键功能
        gsyVideoPlayer.fullscreenButton.setOnClickListener {
            //设置横屏
            if (orientationUtils?.isLand != 1) {
                orientationUtils?.resolveByClick()
            }
            gsyVideoPlayer.startWindowFullscreen(
                gsyVideoPlayer.context,
                actionBar = false,
                statusBar = true
            )
        }
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        gsyVideoPlayer.isAutoFullWithSize = false
        //音频焦点冲突时是否释放
        gsyVideoPlayer.isReleaseWhenLossAudio = true
        //全屏动画
        gsyVideoPlayer.isShowFullAnimation = false
        //小屏时不触摸滑动
        gsyVideoPlayer.setIsTouchWiget(false)
        gsyVideoPlayer.setVideoUrl(url)
        gsyVideoPlayer.setVideoCache(cache)
        gsyVideoPlayer.setVideoTitle(title)
    }

    fun savePlayState(videoPlayerView: CoverVideoPlayerView) {
        mVideoView = videoPlayerView.saveState()
        sMediaPlayerListener = videoPlayerView
    }

    fun clonePlayState(videoPlayerView: CoverVideoPlayerView) {
        videoPlayerView.cloneState(mVideoView!!)
    }

    fun release() {
        if (sMediaPlayerListener != null) {
            sMediaPlayerListener!!.onAutoCompletion()
        }
        mVideoView = null
        sMediaPlayerListener = null
    }
}