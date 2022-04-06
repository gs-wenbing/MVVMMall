package com.zwb.module_home.adapter

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.dp2px
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.GoodsEntity
import com.zwb.module_home.R

class PagerListAdapter (layoutID:Int,data: MutableList<GoodsEntity>?) :
    BaseQuickAdapter<GoodsEntity, BaseViewHolder>(layoutID, data) {
    override fun convert(helper: BaseViewHolder, item: GoodsEntity?) {
        item?.let {
            helper.setText(R.id.tvGoodsName,it.goodsName)

            val price = String.format(mContext.getString(R.string.price),it.price.toString())
            helper.setText(
                R.id.tvGoodsPrice, UIUtils.setTextViewContentStyle(price,
                    AbsoluteSizeSpan(mContext.dp2px(18f)),
                    ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.colorPrimary)),
                    2,price.indexOf(".")+1
                ))
            val imageView=helper.getView<ImageView>(R.id.ivIcon)
            val layoutParams = imageView.layoutParams//获取你要填充图片的布局的layoutParam
            layoutParams.width =  (UIUtils.getScreenWidth()-mContext.dp2px(24f)) / 2//这个是布局的宽度
            layoutParams.height = layoutParams.width
            imageView.layoutParams = layoutParams//容器的宽高设置好了
            Glide.with(mContext).load(it.picURL).into(imageView)
//            Glide.with(mContext)
//                .asBitmap()
//                .load(it.picURL)
//                .into(object : CustomTarget<Bitmap>(){
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        val layoutParams = imageView.layoutParams//获取你要填充图片的布局的layoutParam
//                        layoutParams.height = resource.height / resource.width * UIUtils.getScreenWidth() / 2
//                        layoutParams.width =  (UIUtils.getScreenWidth()-mContext.dp2px(24f)) / 2//这个是布局的宽度
//                        imageView.layoutParams = layoutParams//容器的宽高设置好了
//
//                        // 然后在改变一下bitmap的宽高
//                        imageView.setImageBitmap(resource)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {
//
//                    }
//                })
        }
    }

}