package com.zwb.module_classify.fragment

import androidx.fragment.app.activityViewModels
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.module_classify.ClassifyApi
import com.zwb.module_classify.ClassifyViewModel
import com.zwb.module_classify.adapter.ClassifyPAdapter
import com.zwb.module_classify.databinding.FragmentClassifyBinding

class ClassifyFragment : BaseFragment<FragmentClassifyBinding, ClassifyViewModel>() {

    lateinit var mPAdapter: ClassifyPAdapter

    override val mViewModel by activityViewModels<ClassifyViewModel>()

    override fun FragmentClassifyBinding.initView() {
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, mBinding.includeToolbar.toolbar)
        StatusBarUtil.darkMode(requireActivity(),false)
        mBinding.includeToolbar.textSearch.setOnClickListener {
            GoodsServiceWrap.instance.startGoodsList(requireActivity(),"")
        }
        mPAdapter = ClassifyPAdapter(null)
        mBinding.rvLeft.adapter = mPAdapter
        mPAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener{
                _, _, position->
            for ((index, _) in mPAdapter.data.withIndex()){
                mPAdapter.data[index].isSelected = false
                if(position==index){
                    mPAdapter.data[index].isSelected = true
                    mViewModel.mCurrClassify.value = mPAdapter.data[index]
                }
            }
            mPAdapter.notifyDataSetChanged()
        }
        setDefaultLoad(mBinding.layoutContent, ClassifyApi.CLASS_URL)
    }

    override fun initObserve() {
        mViewModel.mClassifyData.observe(viewLifecycleOwner, { response ->
            response?.let {
                it[0].isSelected = true
                mPAdapter.setNewData(it.toMutableList())
                mViewModel.mCurrClassify.value = it[0]
            }
        })
    }

    override fun initRequestData() {
        mViewModel.loadClassifyCo()
    }
}