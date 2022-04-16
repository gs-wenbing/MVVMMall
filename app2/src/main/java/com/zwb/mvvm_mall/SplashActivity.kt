package com.zwb.mvvm_mall

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.mvvm_mall.databinding.ActivitySplashBinding

/**
 * 启动页
 * 可以在这里面加载初始化的数据
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override val mViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // 不显示系统的标题栏，保证windowBackground和界面activity_main的大小一样，显示在屏幕不会有错位（去掉这一行试试就知道效果了）
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
    }

    override fun ActivitySplashBinding.initView() {}

    override fun initObserve() {
        timer.start()
    }

    override fun initRequestData() {

    }
    private var timer: CountDownTimer = object : CountDownTimer(5000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(sin: Long) {
            mBinding.time.text = "" + sin / 1000 + " S"
            if(sin / 1000==1L){
                HomeTabActivity.launch(this@SplashActivity)
                finish()
            }
        }

        override fun onFinish() {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

}
