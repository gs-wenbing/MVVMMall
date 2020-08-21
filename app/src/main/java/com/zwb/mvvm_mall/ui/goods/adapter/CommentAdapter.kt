package com.zwb.mvvm_mall.ui.goods.adapter

import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.CommentEntity
import com.zwb.mvvm_mall.bean.CommentPics

class CommentAdapter(data: MutableList<CommentEntity>?) :
    BaseQuickAdapter<CommentEntity, BaseViewHolder>(R.layout.item_goods_comment_layout, data) {

    override fun convert(helper: BaseViewHolder, item: CommentEntity?) {
        item?.let {
            helper.setText(R.id.tvCommentUser,it.userName)
            val userIcon:ImageView = helper.getView(R.id.ivCommentHeader)
            if(!TextUtils.isEmpty(it.userIcon)){
                Glide.with(mContext).load(it.userIcon).into(userIcon)
            }
            userIcon.post {
                BannerUtils.setBannerRound(userIcon, userIcon.height.toFloat())
            }
            helper.setText(R.id.tvCommentDate,it.date)
            helper.setText(R.id.tvReplyNum,it.replyNum.toString())
            helper.setText(R.id.tvStarNum,it.starNum.toString())

            val tvComment:TextView = helper.getView(R.id.tvComment)
            tvComment.text = it.context
            val tvLookMore:TextView = helper.getView(R.id.tvLookMore)
            tvComment.viewTreeObserver.addOnGlobalLayoutListener {
                if(tvComment.lineCount>=3){
                    tvLookMore.visibility = View.VISIBLE
                }else{
                    tvLookMore.visibility = View.GONE
                }
            }
            helper.addOnClickListener(R.id.tvLookMore)
            tvLookMore.tag = it.ID
            tvLookMore.setOnClickListener { _ ->
                setTextViewExpanded(true,it,tvLookMore,tvComment)
            }
            setTextViewExpanded(false,it,tvLookMore,tvComment)

            val picRecyclerView:RecyclerView = helper.getView(R.id.picRecyclerView)
            if(it.pics.isNotEmpty()){
                picRecyclerView.visibility = View.VISIBLE
                setCommentData(it.pics,picRecyclerView)
            }else{
                picRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setTextViewExpanded(isClick:Boolean,it:CommentEntity,tvLookMore:TextView,tvComment:TextView){
        if(isClick == it.isExpanded){
            tvLookMore.text = "查看"
            tvComment.ellipsize = TextUtils.TruncateAt.END
            tvComment.maxLines = 3
        }else{
            tvLookMore.text = "收起"
            tvComment.ellipsize = null
            tvComment.maxLines = Int.MAX_VALUE
        }
        if(isClick) it.isExpanded = !it.isExpanded
    }

    private fun setCommentData(pics: List<CommentPics>, picRecyclerView: RecyclerView){
        var type = 3
        when{
            pics.size==1->{
                type = 1
                picRecyclerView.layoutManager = GridLayoutManager(mContext,1)
            }
            pics.size==2->{
                type = 2
                picRecyclerView.layoutManager = GridLayoutManager(mContext,2)
            }
            (pics.size in 3..6) ->{
                type = 3
                picRecyclerView.layoutManager = GridLayoutManager(mContext,3)
            }
            pics.size>6->{
                type = 4
                picRecyclerView.layoutManager = GridLayoutManager(mContext,3)
            }
        }
        picRecyclerView.adapter = CommentPicAdapter(type,pics.toMutableList())
    }
}