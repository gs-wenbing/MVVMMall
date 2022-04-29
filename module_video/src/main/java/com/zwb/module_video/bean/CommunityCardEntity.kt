package com.zwb.module_video.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

data class CommunityCardEntity(
    var coverUrl: String? = "",
    var description: String? = "",
    var avatarUrl: String? = "",
    var nickName: String? = "",
    var collectionCount: Int = 0,
    var imgWidth: Int = 0,
    var imgHeight: Int = 0
): MultiItemEntity {
    override fun getItemType(): Int {
        return AdapterItemType.COMMUNITY_CARD_VIEW
    }
}