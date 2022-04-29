package com.zwb.module_video.bean

import com.zwb.module_video.adapter.AdapterItemType

class VideoSmallEntity : VideoEntity() {

    override fun getItemType(): Int {
        return AdapterItemType.VIDEO_SMALL_CARD_VIEW
    }

}