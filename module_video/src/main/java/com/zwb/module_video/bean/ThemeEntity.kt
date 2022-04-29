package com.zwb.module_video.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

data class ThemeEntity(
    var id: Long,
    var title: String? = "",//2020总结,
    var description: String? = "",//
    var icon: String? = "",//http://img.kaiyanapp.com/804a06422807f5d39d5cd5c19c4769c5.jpeg?imageMogr2/quality/60/format/jpg,
    var actionUrl: String? = "",//eyepetizer://webview/?title=&amp;url=https%3A%2F%2Fh5.eyepetizer.net%2Fv1%2Fannual2020,
) : MultiItemEntity {
    override fun getItemType(): Int {
        return AdapterItemType.THEME_CARD_VIEW
    }
}