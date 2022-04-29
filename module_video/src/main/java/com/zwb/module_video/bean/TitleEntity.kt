package com.zwb.module_video.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

data class TitleEntity(
    var text: String? = "",
    var actionUrl: String? = "",
    var rightText: String? = "",
) : MultiItemEntity {
    override fun getItemType(): Int {
        return AdapterItemType.TITLE_VIEW
    }
}