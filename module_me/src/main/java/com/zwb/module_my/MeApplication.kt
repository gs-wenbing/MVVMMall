package com.zwb.module_my

import com.zwb.lib_base.BaseApplication

class MeApplication:BaseApplication() {
    override fun onCreate() {
        super.onCreate()
//        EventBus
//            .builder()
//            .addIndex(module_meEventIndex())
//            .installDefaultEventBus()
    }
}