package com.zwb.lib_common.service.goods

import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.template.IProvider

interface IGoodsService: IProvider {

    fun startGoodsList(activity: FragmentActivity, searchKey: String)

    fun startGoodsDetail(activity: FragmentActivity, goodsName: String)

}