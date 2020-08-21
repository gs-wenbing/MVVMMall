package com.zwb.mvvm_mall.ui.goods.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils
import android.content.Context
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.GoodsBannerEntity


class GoodsBannerAdapter(var context: Context,imageUrls: List<GoodsBannerEntity>) : BannerAdapter<GoodsBannerEntity, RecyclerView.ViewHolder>(imageUrls) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return ImageHolder(BannerUtils.getView(parent, R.layout.banner_image))
            2 -> return VideoHolder(BannerUtils.getView(parent, R.layout.banner_video))
        }
        return ImageHolder(BannerUtils.getView(parent, R.layout.banner_image))
    }
    override fun getItemViewType(position: Int): Int {
        return getData(getRealPosition(position)).viewType
    }

    override fun onBindView(holder: RecyclerView.ViewHolder, data: GoodsBannerEntity, position: Int, size: Int) {
        when (holder.itemViewType) {
            1 -> {
                val imageHolder = holder as ImageHolder
                Glide.with(imageHolder.itemView)
                    .load(data.imageUrl)
                    .into(imageHolder.imageView)
            }
            2 -> {
                val videoHolder = holder as VideoHolder
                videoHolder.player.setUp(data.imageUrl, true, null)
                videoHolder.player.backButton.visibility = View.GONE
                videoHolder.player.fullscreenButton.visibility = View.GONE
//                videoHolder.player.startPlayLogic()
                //增加封面
                val imageView = ImageView(context)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.setImageResource(R.mipmap.home_grmz)
                Glide.with(context)
                    .load("http://img13.360buyimg.com/n1/s800x1026_jfs/t1/45710/39/4373/326364/5d219e37Eb4d39bac/32f0f9a45c3fa5b1.jpg!cc_800x1026.jpg")
                    .into(imageView)
                videoHolder.player.thumbImageView = imageView
            }
        }
    }

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }
    class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        var player: NormalGSYVideoPlayer = view.findViewById(R.id.bannerPlayer)
    }
}