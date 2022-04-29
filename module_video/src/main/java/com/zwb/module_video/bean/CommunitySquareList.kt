package com.zwb.module_video.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

data class CommunitySquareList(
    var list: List<CommunitySquareEntity?>
): MultiItemEntity {
    override fun getItemType(): Int {
        return AdapterItemType.COMMUNITY_SQUARE_VIEW
    }
}

data class CommunitySquareEntity(
    var title: String? = "",//主题创作广场
    var subTitle: String? = "",//发布你的作品和日常
    var bgPicture: String? = "",//http://img.kaiyanapp.com/04eef7e9f3b14a597bd04a8d81a4c8f3.png?imageMogr2/quality/60/format/jpg,
    var actionUrl: String? = ""//eyepetizer://webview/?title=&url=https%3a%2f%2fm.eyepetizer.net%2fu2%2funiversal-card%3fpage_label%3dtopic_square%26page_type%3dcard
)