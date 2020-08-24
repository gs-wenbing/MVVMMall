package com.zwb.mvvm_mall.ui.classify.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.ui.classify.adapter.ClassifyRightAdapter
import com.zwb.mvvm_mall.ui.classify.viewmodel.ClassifyViewModel
import kotlinx.android.synthetic.main.fragment_classify_right.*


class ClassifyFragmentRight : BaseVMFragment<ClassifyViewModel>() {

    override var layoutId: Int = R.layout.fragment_classify_right

    lateinit var mPAdapter: ClassifyRightAdapter

    override fun initView() {
        super.initView()
        mPAdapter = ClassifyRightAdapter(null)
        rvRight.layoutManager = GridLayoutManager(activity,3)
        rvRight.adapter = mPAdapter
//        val screenWidth = ScreenUtils.getScreenWidth() //屏幕宽度
//        val itemWidth = PixelUtils.dp2px(activity, 90) //每个item的宽度
//        rvRight.addItemDecoration(SpaceItemDecoration((screenWidth - itemWidth * 3) / 6))
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
