package com.zwb.module_video.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

data class CategoryList(
    var categoryList: List<CategoryEntity?>
) : MultiItemEntity {
    override fun getItemType(): Int {
        return AdapterItemType.CATEGORY_CARD_VIEW
    }
}

data class CategoryEntity(
    var id: Long,
    var title: String? = "",//2020 开眼圣诞集赞活动,
    var description: String? = "",
    var image: String? = "",//http://img.kaiyanapp.com/f8d18532be27c053b8ac68922361b866.jpeg?imageMogr2/quality/60/format/jpg,
    var actionUrl: String? = "",//eyepetizer://webview/?title=&amp;url=https%3A%2F%2Fwww.kaiyanapp.com%2Fnew_article.html%3Fnid%3D2529%26shareable%3Dtrue%26type%3DarticleTopic%26cid%3D2273,
)