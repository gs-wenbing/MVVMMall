package com.zwb.module_video.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.OrientationEventListener
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_video.VideoViewModel
import com.zwb.module_video.adapter.VideoDetailAdapter
import com.zwb.module_video.bean.VideoEntity
import com.zwb.module_video.databinding.ActivityPlayerBinding
import com.zwb.module_video.databinding.VideoItemHeaderLayoutBinding
import com.zwb.module_video.view.VideoPlayerHelper

class VideoPlayerActivity : BaseActivity<ActivityPlayerBinding, VideoViewModel>() {

    override val mViewModel by viewModels<VideoViewModel>()

    private val mAdapter = VideoDetailAdapter(mutableListOf())

    // 旋转帮助类
    private var orientationUtils: OrientationUtils? = null

    private var isPause = false

    private var videoEntity: VideoEntity? = null


    override fun ActivityPlayerBinding.initView() {
        videoEntity = intent.getParcelableExtra("video") as VideoEntity
        // 全屏辅助
        orientationUtils = OrientationUtils(this@VideoPlayerActivity, mBinding.cvVideoView)

        init()
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.data[position]
            if(item is VideoEntity){
                videoEntity = item
                init()
                initRequestData()
            }
        }
    }

    private fun init(){
        val layoutManager = LinearLayoutManager(this@VideoPlayerActivity)
        mBinding.rvVideoList.layoutManager = layoutManager
        mBinding.rvVideoList.adapter = mAdapter
        mBinding.refreshLayout.setEnableRefresh(false)

        initVideo()
        initHeaderView()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getVideoRelatedAndReplies(videoEntity!!.videoId).observe(this,{
            mAdapter.setNewData(it)
        })
    }
    override fun setStatusBar() {
        StatusBarUtil.immersive(this)
        StatusBarUtil.darkMode(this, false)
    }

    @SuppressLint("SetTextI18n")
    private fun initHeaderView(){
        videoEntity?.let {
            mAdapter.removeAllHeaderView()
            val headerBinding = VideoItemHeaderLayoutBinding.inflate(layoutInflater)
            mAdapter.addHeaderView(headerBinding.root)
            BannerUtils.setBannerRound(headerBinding.ivAuthor, UIUtils.dp2px(40f).toFloat())
            Glide.with(this)
                .load(it.authorIcon)
                .into(headerBinding.ivAuthor)

            headerBinding.tvVideoTitle.text = it.title
            headerBinding.tvCategory.text = "# ${it.category}"
            headerBinding.tvVideoDescription.text = it.videoDescription
            headerBinding.tvNikeName.text = it.nickName
            headerBinding.tvAuthorDescription.text = it.authorDescription

        }


    }
    private fun initVideo() {
        Glide.with(this)
                .asBitmap()
                .load(videoEntity!!.blurredUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val drawable: Drawable = BitmapDrawable(resources, resource)
                        mBinding.layout.background = drawable
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        mBinding.cvVideoView.backButton.setOnClickListener { finish() }

        // 初始化不打开外部的旋转
        orientationUtils!!.isEnable = false

        // 初始化配置
        VideoPlayerHelper.optionPlayer(
            mBinding.cvVideoView,
            videoEntity!!.playerUrl!!,
            true,
            videoEntity!!.title!!,
            orientationUtils
        )

        mBinding.cvVideoView.setIsTouchWiget(true)

        mBinding.cvVideoView.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                // 开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.backToProtVideo()
            }
        })

        mBinding.cvVideoView.startPlayLogic()

        orientationUtils?.isClickLand = true
    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils?.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        mBinding.cvVideoView.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        mBinding.cvVideoView.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.cvVideoView.gsyVideoManager
            .setListener(mBinding.cvVideoView.gsyVideoManager.lastListener())
        mBinding.cvVideoView.gsyVideoManager.setLastListener(null)
        mBinding.cvVideoView.cancel()
        GSYVideoManager.releaseAllVideos()
        orientationUtils?.releaseListener()
        try {
            // 第三方库中的内存泄漏,只能利用反射来解决
            val mOrientationEventListener = OrientationUtils::class.java
                .getDeclaredField("mOrientationEventListener")
            val contextField = OrientationUtils::class.java.getField("mActivity")
            contextField.isAccessible = true
            contextField[orientationUtils] = null
            mOrientationEventListener.isAccessible = true
            val listener =
                mOrientationEventListener[orientationUtils] as OrientationEventListener
            val mSensorEventListener = OrientationEventListener::class.java
                .getDeclaredField("mSensorEventListener")
            mSensorEventListener.isAccessible = true
            mSensorEventListener[listener] = null
            val mSensorManager = OrientationEventListener::class.java
                .getDeclaredField("mSensorManager")
            mSensorManager.isAccessible = true
            mSensorManager[listener] = null
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        orientationUtils = null
        VideoPlayerHelper.release()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // 如果旋转了就全屏
        if (!isPause) {
            mBinding.cvVideoView.onConfigurationChanged(
                this,
                newConfig,
                orientationUtils,
                true,
                true
            )
        }
    }

    companion object {
        fun launch(activity: FragmentActivity, video: VideoEntity) =
            activity.apply {
                val intent = Intent(this, VideoPlayerActivity::class.java)
                intent.putExtra("video", video)
                startActivity(intent)
            }
    }
}