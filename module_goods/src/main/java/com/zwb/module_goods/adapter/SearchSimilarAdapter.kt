package com.zwb.module_goods.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.module_goods.R

class SearchSimilarAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_similar, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        item?.let {
            helper.setText(R.id.tvHeader,it)
        }
    }
}