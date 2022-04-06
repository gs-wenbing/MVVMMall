package com.zwb.module_classify.adapter

import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.module_classify.R
import com.zwb.module_classify.bean.ClassifyEntity

class ClassifyPAdapter(data: MutableList<ClassifyEntity>?) :
    BaseQuickAdapter<ClassifyEntity, BaseViewHolder>(R.layout.item_simple, data) {

    override fun convert(helper: BaseViewHolder, item: ClassifyEntity?) {
        item?.let {
            helper.setText(R.id.textView,it.goodsClassName)
            if(item.isSelected){
                helper.setVisible(R.id.v_line, true)
                helper.setBackgroundRes(R.id.item_layout,R.drawable.classisy_selected)
                helper.getView<TextView>(R.id.textView).typeface = Typeface.DEFAULT_BOLD
            }else{
                helper.setVisible(R.id.v_line, false)
                helper.setBackgroundRes(R.id.item_layout,R.drawable.classisy_unselected)
                helper.getView<TextView>(R.id.textView).typeface = Typeface.DEFAULT
            }

        }

    }

}