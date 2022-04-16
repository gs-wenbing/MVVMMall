package com.zwb.module_classify.fragment

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
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
                GoodsServiceWrap.instance.startGoodsList(requireActivity(),(adapter.getItem(position) as ClassifySectionEntity).t.goodsClassName)
            }
        }
    }

    override fun initObserve() {
        mViewModel.mCurrClassify.observe(viewLifecycleOwner, { response->
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
