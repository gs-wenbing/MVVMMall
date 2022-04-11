package com.zwb.module_goods.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.module_goods.R
import com.zwb.module_goods.bean.CommentPics


class CommentPicAdapter(var type:Int,data: MutableList<CommentPics>?) :
    BaseQuickAdapter<CommentPics, BaseViewHolder>(R.layout.item_goods_comment_pic_layout, data) {

    override fun convert(helper: BaseViewHolder, item: CommentPics?) {
        item?.let {
            val ivCommentPic:ImageView = helper.getView(R.id.ivCommentPic)
            Glide.with(mContext).load(it.url).into(ivCommentPic)
            val params = ivCommentPic.layoutParams
            when(type){
                1->{
                    params.height = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))*2/3
                    params.width = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))*2/3
                }
                2->{
                    params.height = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))/2
                    params.width = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))/2
                }
                3->{
                    params.height = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))/3
                    params.width = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))/3
                }
                4->{
                    params.height = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))/3
                    params.width = (UIUtils.getScreenWidth()-UIUtils.dp2px(44f))/3
                }
            }
            ivCommentPic.layoutParams = params
            ivCommentPic.post {
                BannerUtils.setBannerRound(ivCommentPic, 20f)
            }
        }
    }

}