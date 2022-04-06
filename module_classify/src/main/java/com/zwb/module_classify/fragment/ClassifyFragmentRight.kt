package com.zwb.module_classify.fragment

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.module_classify.ClassifyViewModel
import com.zwb.module_classify.adapter.ClassifyRightAdapter
import com.zwb.module_classify.bean.ClassifySectionEntity
import com.zwb.module_classify.databinding.FragmentClassifyRightBinding


class ClassifyFragmentRight : BaseFragment<FragmentClassifyRightBinding,ClassifyViewModel>() {

    lateinit var mPAdapter: ClassifyRightAdapter

    override val mViewModel by activityViewModels<ClassifyViewModel>()

    override fun FragmentClassifyRightBinding.initView() {
        mPAdapter = ClassifyRightAdapter(null)
        rvRight.layoutManager = GridLayoutManager(activity,3)
        rvRight.adapter = mPAdapter
        mPAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as ClassifySectionEntity
            if(!item.isHeader){
//                SearchGoodsActivity.launch(requireActivity(),
//                    (adapter.getItem(position) as ClassifySectionEntity).t.goodsClassName)
            }
        }
    }

    override fun initObserve() {
        mViewModel.mCurrClassify.observe(this, { response->
            response?.let {
                mViewModel.loadRightClassify(it)
            }
        })
        mViewModel.mClassifyRightData.observe(this, { response ->
            response?.let {
                mPAdapter.setNewData(it.toMutableList())
            }
        })
    }

    override fun initRequestData() {
    }


}
