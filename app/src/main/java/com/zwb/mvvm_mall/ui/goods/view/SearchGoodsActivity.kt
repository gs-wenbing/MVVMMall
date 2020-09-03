package com.zwb.mvvm_mall.ui.goods.view

import android.content.Intent
import android.text.TextUtils
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMActivity
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.common.utils.KeyBoardUtils
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.ui.cart.view.CartFragment
import com.zwb.mvvm_mall.ui.classify.view.ClassifyFragment
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import com.zwb.mvvm_mall.ui.home.view.HomeFragment
import com.zwb.mvvm_mall.ui.mine.view.MineFragment
import kotlinx.android.synthetic.main.activity_goods_search.*
import kotlinx.android.synthetic.main.layout_search_toolbar.*




class SearchGoodsActivity :BaseVMActivity<GoodsViewModel>(){

    private var mLastIndex: Int = -1
    private val mFragmentMap:MutableMap<Int,Fragment> = HashMap()
    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    override val layoutId = R.layout.activity_goods_search

    override fun initView() {
        super.initView()
        StatusBarUtil.darkMode(this,true)
        tvSearch.visibility = View.VISIBLE
        ivRight.visibility = View.GONE
        //处理返回键逻辑
        ivBack.setOnClickListener {
            if(mCurrentFragment is SearchGoodsFragment){
                switchFragment(Constant.SEARCH_RECORD)
                setEditFocus()
            }else{
                finish()
            }
        }
        //点击输入框
        textSearch.setOnTouchListener { _, event ->
            event?.let {
                if(it.action == MotionEvent.ACTION_UP)
                    if(mCurrentFragment is SearchGoodsFragment){
                        mViewModel.mSearchStatus.value = SearchStatus_SIMILAR
                        setEditFocus()
                    }
            }
            false
        }

       //点击搜索按钮
        tvSearch.setOnClickListener {
            var searchKey = textSearch.hint.toString()
            if(textSearch.text!=null && !TextUtils.isEmpty(textSearch.text.toString())){
                searchKey = textSearch.text.toString()
            }
            mViewModel.mSearchKey.value = searchKey
            mViewModel.mSearchStatus.value = SearchStatus_GOODS
            clearFocus()
        }
        ivRight.setOnClickListener {
            if(mFragmentMap[Constant.SEARCH_GOODS]!=null){
                (mFragmentMap[Constant.SEARCH_GOODS] as SearchGoodsFragment).switchList(ivRight)
            }
        }
        ivDelete.setOnClickListener {
            mViewModel.mSearchKey.value = ""
            mViewModel.mSearchStatus.value = SearchStatus_RECORD
        }
        layoutVoice.setOnClickListener {
            Toast.makeText(this,"语音",Toast.LENGTH_SHORT).show()
        }
    }

    override fun initData() {
        val searchKey= intent.getStringExtra(SearchKey)
        if(!TextUtils.isEmpty(searchKey)){
            mViewModel.mSearchKey.value = intent.getStringExtra(SearchKey)
            mViewModel.mSearchStatus.value = SearchStatus_GOODS
        }else{
            mViewModel.mSearchStatus.value = SearchStatus_RECORD
        }
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSearchStatus.observe(this, Observer {
            when (it) {
                SearchStatus_RECORD -> {//切换到搜索记录的fragment
                    switchFragment(Constant.SEARCH_RECORD)
                    setEditFocus()
                    ivDelete.visibility = View.GONE
                }
                SearchStatus_SIMILAR -> {//切换到搜索类似关键词的fragment
                    switchFragment(Constant.SEARCH_SIMILAR)
                    setEditFocus()
                    ivDelete.visibility = View.VISIBLE
                }
                SearchStatus_GOODS -> {//切换到搜索商品列表的fragment
                    switchFragment(Constant.SEARCH_GOODS)
                    clearFocus()
                    ivDelete.visibility = View.GONE
                }
            }
        })

        mViewModel.mSearchKey.observe(this, Observer {
            textSearch.setText(mViewModel.mSearchKey.value)
            val b = textSearch.text
            textSearch.setSelection(b.length)
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
                Constant.SEARCH_GOODS -> fragment = SearchGoodsFragment.newInstance()
                Constant.SEARCH_RECORD -> fragment = SearchRecordFragment.newInstance()
                Constant.SEARCH_SIMILAR -> fragment = SearchSimilarFragment.newInstance()
            }
            mFragmentMap[key] = fragment!!
        }
        return fragment
    }
    private fun setEditFocus(){
        textSearch.isFocusable = true
        textSearch.isFocusableInTouchMode = true
        textSearch.requestFocus()
        layoutVoice.visibility = View.VISIBLE
        ivRight.visibility = View.GONE
        tvSearch.visibility = View.VISIBLE
    }

    private fun clearFocus(){
        KeyBoardUtils.hideInputForce(this)
        textSearch.clearFocus()
        textSearch.isFocusable = false
        layoutVoice.visibility = View.GONE
        ivRight.visibility = View.VISIBLE
        tvSearch.visibility = View.GONE
    }

    override fun onBackPressed() {
        if(mCurrentFragment is SearchGoodsFragment){
            switchFragment(Constant.SEARCH_RECORD)
            setEditFocus()
        }else{
            finish()
        }
    }

    companion object{
        const val SearchStatus_RECORD=1001
        const val SearchStatus_SIMILAR=1002
        const val SearchStatus_GOODS=1003
        const val SearchKey="search_key"

        fun launch(activity: FragmentActivity,keyValue:String) =
            activity.apply {
                val intent = Intent(this, SearchGoodsActivity::class.java)
                intent.putExtra(SearchKey,keyValue)
                startActivity(intent)
            }
    }
}