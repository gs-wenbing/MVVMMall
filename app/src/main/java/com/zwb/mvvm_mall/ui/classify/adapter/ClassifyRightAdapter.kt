package com.zwb.mvvm_mall.ui.classify.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.ClassifySectionEntity

class ClassifyRightAdapter(data: MutableList<ClassifySectionEntity>?) :
    BaseSectionQuickAdapter<ClassifySectionEntity, BaseViewHolder>(R.layout.item_classify,R.layout.item_classify_header, data) {
    override fun convertHead(helper: BaseViewHolder?, item: ClassifySectionEntity?) {
        helper?.setText(R.id.tvHeader,item?.header)
    }

    override fun convert(helper: BaseViewHolder, item: ClassifySectionEntity?) {
        item?.let {
            helper.setText(R.id.tvTitle,it.t.goodsClassName)
            Glide.with(mContext).load(it.t.picURL).into(helper.getView(R.id.ivIcon))
        }
    }

}