package com.zwb.module_goods.activity

import android.content.Intent
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.ktx.getStatusBarHeight
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.KeyBoardUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.module_goods.GoodsViewModel
import com.zwb.module_goods.R
import com.zwb.module_goods.databinding.ActivityGoodsBinding
import com.zwb.module_goods.fragment.SearchGoodsFragment
import com.zwb.module_goods.fragment.SearchRecordFragment
import com.zwb.module_goods.fragment.SearchSimilarFragment

@Route(path = RoutePath.Goods.PAGE_GOODS_LIST)
class GoodsListActivity : BaseActivity<ActivityGoodsBinding, GoodsViewModel>() {

    @Autowired(name = Constants.Goods.PARAMS_SEARCH_KEY)
    lateinit var searchKey: String

    private var mLastIndex: Int = -1
    private val mFragmentMap: MutableMap<Int, Fragment> = HashMap()

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    override val mViewModel by viewModels<GoodsViewModel>()

    override fun ActivityGoodsBinding.initView() {
        mBinding.includeToolbar.tvSearch.visibility = View.VISIBLE
        mBinding.includeToolbar.ivRight.visibility = View.GONE

        //处理返回键逻辑
        mBinding.includeToolbar.ivBack.setOnClickListener {
            if (mCurrentFragment is SearchGoodsFragment) {
                switchFragment(SEARCH_RECORD)
                setEditFocus()
            } else {
                finish()
            }
        }

        //点击输入框
        mBinding.includeToolbar.textSearch.setOnTouchListener { _, event ->
            event?.let {
                if (it.action == MotionEvent.ACTION_UP)
                    if (mCurrentFragment is SearchGoodsFragment) {
                        mViewModel.mSearchStatus.value = SearchStatus_SIMILAR
                        setEditFocus()
                    }
            }
            false
        }

        //点击搜索按钮
        mBinding.includeToolbar.tvSearch.setOnClickListener {
            var searchKey = mBinding.includeToolbar.textSearch.hint.toString()
            if (mBinding.includeToolbar.textSearch.text != null
                && !TextUtils.isEmpty(mBinding.includeToolbar.textSearch.text.toString())
            ) {
                searchKey = mBinding.includeToolbar.textSearch.text.toString()
            }
            mViewModel.mSearchKey.value = searchKey
            mViewModel.mSearchStatus.value = SearchStatus_GOODS
            clearFocus()
        }
        mBinding.includeToolbar.ivRight.setOnClickListener {
            if (mFragmentMap[SEARCH_GOODS] != null) {
                (mFragmentMap[SEARCH_GOODS] as SearchGoodsFragment).switchList(mBinding.includeToolbar.ivRight)
            }
        }
        mBinding.includeToolbar.ivDelete.setOnClickListener {
            mViewModel.mSearchKey.value = ""
            mViewModel.mSearchStatus.value = SearchStatus_RECORD
        }
        mBinding.viewVoice.setOnClickListener {
            Toast.makeText(applicationContext, "语音", Toast.LENGTH_SHORT).show()
        }
    }
    override fun setStatusBar() {
        StatusBarUtil.darkMode(this,true)
//        super.setStatusBar()
//        StatusBarUtil.setPaddingSmart(this, mBinding.includeToolbar.toolbar)
//        StatusBarUtil.setMargin(this,mBinding.layoutContent)
    }
    override fun initRequestData() {
        if (!TextUtils.isEmpty(searchKey)) {
            mViewModel.mSearchKey.value = searchKey
            mViewModel.mSearchStatus.value = SearchStatus_GOODS
        } else {
            mViewModel.mSearchStatus.value = SearchStatus_RECORD
        }
    }

    override fun initObserve() {
        mViewModel.mSearchStatus.observe(this, {
            when (it) {
                SearchStatus_RECORD -> {//切换到搜索记录的fragment
                    switchFragment(SEARCH_RECORD)
                    setEditFocus()
                    mBinding.includeToolbar.ivDelete.visibility = View.GONE
                }
                SearchStatus_SIMILAR -> {//切换到搜索类似关键词的fragment
                    switchFragment(SEARCH_SIMILAR)
                    setEditFocus()
                    mBinding.includeToolbar.ivDelete.visibility = View.VISIBLE
                }
                SearchStatus_GOODS -> {//切换到搜索商品列表的fragment
                    switchFragment(SEARCH_GOODS)
                    clearFocus()
                    mBinding.includeToolbar.ivDelete.visibility = View.GONE
                }
            }
        })

        mViewModel.mSearchKey.observe(this, {
            mBinding.includeToolbar.textSearch.setText(mViewModel.mSearchKey.value)
            val b = mBinding.includeToolbar.textSearch.text
            mBinding.includeToolbar.textSearch.setSelection(b.length)
        })
    }


    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.layoutContent, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.layoutContent, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
    }

    private fun getFragment(key: Int): Fragment {
        var fragment: Fragment? = mFragmentMap[key]
        if (fragment == null) {
            when (key) {
                SEARCH_GOODS -> fragment = SearchGoodsFragment()
                SEARCH_RECORD -> fragment = SearchRecordFragment()
                SEARCH_SIMILAR -> fragment = SearchSimilarFragment()
            }
            mFragmentMap[key] = fragment!!
        }
        return fragment
    }

    private fun setEditFocus() {
        mBinding.includeToolbar.textSearch.isFocusable = true
        mBinding.includeToolbar.textSearch.isFocusableInTouchMode = true
        mBinding.includeToolbar.textSearch.requestFocus()
        mBinding.viewVoice.visible()
        mBinding.includeToolbar.ivRight.gone()
        mBinding.includeToolbar.tvSearch.visible()
    }

    private fun clearFocus() {
        KeyBoardUtils.hideInputForce(this)
        mBinding.includeToolbar.textSearch.clearFocus()
        mBinding.includeToolbar.textSearch.isFocusable = false
        mBinding.viewVoice.gone()
        mBinding.includeToolbar.ivRight.visible()
        mBinding.includeToolbar.tvSearch.gone()
    }


    companion object {
        const val SEARCH_RECORD = 0
        const val SEARCH_GOODS = 1
        const val SEARCH_SIMILAR = 2

        const val SearchStatus_RECORD = 1001
        const val SearchStatus_SIMILAR = 1002
        const val SearchStatus_GOODS = 1003
        const val SearchKey = "search_key"

        fun launch(activity: FragmentActivity, searchKey: String) =
            activity.apply {
                val intent = Intent(this, GoodsListActivity::class.java)
                intent.putExtra(SearchKey, searchKey)
                startActivity(intent)
            }
    }


}
