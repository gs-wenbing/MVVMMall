package com.zwb.module_video.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils
import com.zwb.module_video.bean.BannerEntity

class BannerVideoAdapter(imageUrls: List<BannerEntity>) :
    BannerAdapter<BannerEntity, BannerVideoAdapter.ImageHolder>(imageUrls) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent!!.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 20f)
        return ImageHolder(imageView)
    }


    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }

    override fun onBindView(
        holder: ImageHolder?,
        data: BannerEntity?,
        position: Int,
        size: Int
    ) {
        Glide.with(holder!!.itemView)
            .load(data!!.image)
            .into(holder.imageView)
    }

}