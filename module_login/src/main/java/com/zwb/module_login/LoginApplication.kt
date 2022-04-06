package com.zwb.module_login

import com.zwb.lib_base.BaseApplication
import org.greenrobot.eventbus.EventBus

class LoginApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
//        EventBus
//            .builder()
////            .addIndex("各模块生成的订阅类的实例 类名在base_module.gradle脚本中进行了设置 比如 module_home 生成的订阅类就是 module_homeIndex")
//            .installDefaultEventBus()
    }
}