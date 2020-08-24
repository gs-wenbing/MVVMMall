package com.zwb.mvvm_mall.ui.goods.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.ui.goods.adapter.CommentAdapter
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_goods_comment.*




class GoodsCommentFragment : BaseVMFragment<GoodsViewModel>() {

    override var layoutId: Int = R.layout.fragment_goods_comment


    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mCommentList.observe(this, Observer {
            rvComment.layoutManager = LinearLayoutManager(mActivity)
            rvComment.adapter = CommentAdapter(it.toMutableList())
        })
    }
    companion object {
        fun newInstance(goodsID:Int): GoodsCommentFragment {
            val f = GoodsCommentFragment()
            val args = Bundle()
            args.putInt("ID",goodsID)
            f.arguments = args
           return f
        }
    }
}
