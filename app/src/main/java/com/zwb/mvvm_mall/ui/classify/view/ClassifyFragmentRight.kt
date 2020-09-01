package com.zwb.mvvm_mall.ui.classify.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.ClassifySectionEntity
import com.zwb.mvvm_mall.bean.GoodsEntity
import com.zwb.mvvm_mall.ui.classify.adapter.ClassifyRightAdapter
import com.zwb.mvvm_mall.ui.classify.viewmodel.ClassifyViewModel
import com.zwb.mvvm_mall.ui.goods.view.GoodsDetailActivity
import com.zwb.mvvm_mall.ui.goods.view.SearchGoodsActivity
import kotlinx.android.synthetic.main.fragment_classify_right.*


class ClassifyFragmentRight : BaseVMFragment<ClassifyViewModel>() {

    override var layoutId: Int = R.layout.fragment_classify_right

    lateinit var mPAdapter: ClassifyRightAdapter

    override fun initView() {
        super.initView()
        mPAdapter = ClassifyRightAdapter(null)
        rvRight.layoutManager = GridLayoutManager(activity,3)
        rvRight.adapter = mPAdapter
        mPAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as ClassifySectionEntity
            if(!item.isHeader){
                SearchGoodsActivity.launch(requireActivity(),
                    (adapter.getItem(position) as ClassifySectionEntity).t.goodsClassName)
            }
        }
    }

    override fun initData() {
        super.initData()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mCurrClassify.observe(this, Observer { response->
            response?.let {
                mViewModel.loadRightClassify(it)
            }
        })
        mViewModel.mClassifyRightData.observe(this, Observer { response->
            response?.let {
                mPAdapter.setNewData(it.toMutableList())
            }
        })
    }

}
