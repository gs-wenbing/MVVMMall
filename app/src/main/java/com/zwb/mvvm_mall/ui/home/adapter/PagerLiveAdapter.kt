package com.zwb.mvvm_mall.ui.home.adapter

import android.app.PendingIntent.getActivity
import android.graphics.Bitmap
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.common.utils.UIUtils
import android.widget.ImageView


class PagerLiveAdapter (layoutID:Int, data: MutableList<GoodsEntity>?) :
    BaseQuickAdapter<GoodsEntity, BaseViewHolder>(layoutID, data) {
    override fun convert(helper: BaseViewHolder, item: GoodsEntity?) {
        item?.let {
            helper.setText(R.id.tvGoodsName,it.goodsName)


            val price = String.format(mContext.getString(R.string.price),it.price.toString())
            helper.setText(
                R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(price,
                    AbsoluteSizeSpan(UIUtils.dp2px(18f)),
                    ForegroundColorSpan(mContext.getColor(R.color.mainRed)),
                    2,price.indexOf(".")+1
                ))
            val imageView=helper.getView<ImageView>(R.id.ivIcon)
            Glide.with(mContext)
                .asBitmap()
                .load(it.picURL)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layoutParams = imageView.layoutParams//获取你要填充图片的布局的layoutParam
                        layoutParams.height = resource.height / resource.width * UIUtils.getScreenWidth() / 2
                        layoutParams.width =  (UIUtils.getScreenWidth()-UIUtils.dp2px(24f)) / 2//这个是布局的宽度
                        imageView.layoutParams = layoutParams//容器的宽高设置好了
                        // 然后在改变一下bitmap的宽高
                        imageView.setImageBitmap(resource)

                    }

                })

        }
    }

}