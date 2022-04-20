package com.zwb.module_me

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