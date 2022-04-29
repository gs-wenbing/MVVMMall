package com.zwb.module_video.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

data class ReplyEntity(
    var avatar: String? = "",
    var nickName: String? = "",
    var replyMessage: String? = "",
    var releaseTime: Long = 0,
    var likeCount: Int = 0
): MultiItemEntity {
    override fun getItemType(): Int {
        return AdapterItemType.REPLY_VIEW
    }
}