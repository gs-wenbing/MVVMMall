package com.zwb.module_video

import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_video.databinding.ActivityVideoBinding
import com.zwb.module_video.fragment.VideoMainFragment

class VideoActivity : BaseActivity<ActivityVideoBinding, VideoViewModel>() {

    override val mViewModel by viewModels<VideoViewModel>()

    override fun ActivityVideoBinding.initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameContent, VideoMainFragment()).commit()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}