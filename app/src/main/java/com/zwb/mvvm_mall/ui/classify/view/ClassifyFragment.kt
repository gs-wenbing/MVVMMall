package com.zwb.mvvm_mall.ui.classify.view

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.ui.classify.adapter.ClassifyPAdapter
import com.zwb.mvvm_mall.ui.classify.viewmodel.ClassifyViewModel
import com.zwb.mvvm_mall.ui.goods.view.SearchGoodsActivity
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.layout_home_toolbar.*

class ClassifyFragment : BaseVMFragment<ClassifyViewModel>() {

    override var layoutId: Int = R.layout.fragment_classify
    lateinit var mPAdapter:ClassifyPAdapter
    override fun initView() {
        super.initView()
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        StatusBarUtil.darkMode(mActivity,false)
        textSearch.setOnClickListener { SearchGoodsActivity.launch(requireActivity(),"") }
        mPAdapter = ClassifyPAdapter(null)
        rvLeft.adapter = mPAdapter
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
        registerDefaultLoad(layoutContent,Constant.URL_GOODS_CLASS)
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            StatusBarUtil.darkMode(mActivity,false)
        }
    }
    override fun initData() {
        super.initData()
        mViewModel.loadClassifyCo(Constant.URL_GOODS_CLASS)

    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mClassifyData.observe(viewLifecycleOwner, Observer { response->
            response?.let {
                it[0].isSelected = true
                mPAdapter.setNewData(it.toMutableList())
                mViewModel.mCurrClassify.value = it[0]
            }
        })
    }

    companion object {
        fun newInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }
}
