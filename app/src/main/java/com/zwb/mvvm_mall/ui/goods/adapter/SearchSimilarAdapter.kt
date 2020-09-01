package com.zwb.mvvm_mall.ui.goods.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.mvvm_mall.R

class SearchSimilarAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_classify_header, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        item?.let {
            helper.setText(R.id.tvHeader,it)
        }
    }
}