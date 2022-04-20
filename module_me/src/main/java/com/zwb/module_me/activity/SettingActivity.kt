package com.zwb.module_me.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.bean.StringEvent
import com.zwb.lib_common.constant.SpKey
import com.zwb.module_me.MeViewModel
import com.zwb.module_me.databinding.ActivitySettingBinding

class SettingActivity: BaseActivity<ActivitySettingBinding, MeViewModel>() {
    override val mViewModel by viewModels<MeViewModel>()

    override fun ActivitySettingBinding.initView() {
        mBinding.btnLogout.setOnClickListener {
            SpUtils.put(SpKey.IS_LOGIN,false)
            EventBusUtils.postEvent(StringEvent(StringEvent.Event.SWITCH_HOME))
            finish()
        }
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }

    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
    }
}