package com.zwb.module_goods.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.adapter.CommentAdapter
import com.zwb.module_goods.databinding.FragmentGoodsCommentBinding
import kotlinx.android.synthetic.main.fragment_goods_comment.*


class GoodsCommentFragment : BaseFragment<FragmentGoodsCommentBinding,GoodsViewModel>() {
    override val mViewModel by activityViewModels<GoodsViewModel>()


    override fun FragmentGoodsCommentBinding.initView() {
    }

    override fun initObserve() {
        mViewModel.mCommentList.observe(viewLifecycleOwner, Observer {
            rvComment.layoutManager = LinearLayoutManager(requireActivity())
            rvComment.adapter = CommentAdapter(it.toMutableList())
        })
    }

    override fun initRequestData() {

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
