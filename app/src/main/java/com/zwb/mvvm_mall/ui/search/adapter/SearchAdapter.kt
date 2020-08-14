package com.zwb.mvvm_mall.ui.search.adapter

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.bean.SearchHotEntity

class SearchAdapter(data: MutableList<SearchHotEntity>?):
    BaseQuickAdapter<SearchHotEntity, BaseViewHolder>(R.layout.item_search_layout,data){
    override fun convert(helper: BaseViewHolder, item: SearchHotEntity?) {
        item?.let {
            helper.setText(R.id.tvTitle,it.title)
            helper.setText(R.id.tvHotValue,"人气值 "+it.hotValue.toString())
            Glide.with(mContext).load(it.picUrl).into(helper.getView(R.id.ivIcon))
            BannerUtils.setBannerRound(helper.getView(R.id.ivIcon), 20f)
            val tvRanking = helper.getView(R.id.tvRanking) as TextView
            tvRanking.visibility = View.VISIBLE
            when {
                it.ID==1 -> tvRanking.text = "1"
                it.ID==2 -> tvRanking.text = "2"
                it.ID==3 -> tvRanking.text = "3"
                else -> tvRanking.visibility = View.GONE
            }

        }
    }

}