package com.zwb.module_video.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.module_video.adapter.AdapterItemType

open class VideoEntity(
     var coverUrl: String? = "",
     var videoTime: Int = 0,
     var title: String? = "",
     var description: String? = "",
     var category: String? = "",
     var videoDescription: String? = "",
     var authorIcon: String? = "",
     var authorDescription: String? = "",
     var nickName: String? = "",
     var playerUrl: String? = "",
     var blurredUrl: String? = "",
     var videoId: Long = 0
) : MultiItemEntity, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()
    )

    override fun getItemType(): Int {
        return AdapterItemType.VIDEO_CARD_VIEW
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(coverUrl)
        parcel.writeInt(videoTime)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(videoDescription)
        parcel.writeString(authorIcon)
        parcel.writeString(authorDescription)
        parcel.writeString(nickName)
        parcel.writeString(playerUrl)
        parcel.writeString(blurredUrl)
        parcel.writeLong(videoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoEntity> {
        override fun createFromParcel(parcel: Parcel): VideoEntity {
            return VideoEntity(parcel)
        }

        override fun newArray(size: Int): Array<VideoEntity?> {
            return arrayOfNulls(size)
        }
    }


}