package com.zwb.mvvm_mall.ui.goods.view

import android.content.Intent
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMActivity
import com.zwb.mvvm_mall.common.utils.KeyBoardUtils
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.ui.goods.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.activity_goods_search.*
import kotlinx.android.synthetic.main.layout_search_toolbar.*




class SearchGoodsActivity :BaseVMActivity<GoodsViewModel>(){

    private var mRecordFragment:SearchRecordFragment?=null
    private var mGoodsFragment:SearchGoodsFragment?=null
    private var mSimilarFragment:SearchSimilarFragment?=null

    override val layoutId = R.layout.activity_goods_search

    override fun initView() {
        super.initView()
        StatusBarUtil.darkMode(this,true)
        tvSearch.visibility = View.VISIBLE
        ivRight.visibility = View.GONE
        //处理返回键逻辑
        ivBack.setOnClickListener {
            if(mGoodsFragment!=null && mGoodsFragment!!.isVisible){
                showRecordFragment()
                setEditFocus()
            }else{
                finish()
            }
        }
        //点击输入框
        textSearch.setOnTouchListener { _, event ->
            event?.let {
                if(it.action == MotionEvent.ACTION_UP)
                    if(mGoodsFragment!=null && mGoodsFragment!!.isVisible){
                        mViewModel.mSearchStatus.value = SearchStatus_SIMILAR
                        setEditFocus()
                    }
            }
            false
        }

       //点击搜索按钮
        tvSearch.setOnClickListener {
            if((mRecordFragment!=null && mRecordFragment!!.isVisible)
                || (mSimilarFragment!=null && mSimilarFragment!!.isVisible)){
                var searchKey = textSearch.hint.toString()
                if(textSearch.text!=null && !TextUtils.isEmpty(textSearch.text.toString())){
                    searchKey = textSearch.text.toString()
                }
                mViewModel.mSearchKey.value = searchKey
                mViewModel.mSearchStatus.value = SearchStatus_GOODS
                clearFocus()
            }
        }
        ivRight.setOnClickListener {
            if(mGoodsFragment!=null && mGoodsFragment!!.isVisible){
                mGoodsFragment!!.switchList(ivRight)
            }
        }
        ivDelete.setOnClickListener {
            if(mSimilarFragment!=null && mSimilarFragment!!.isVisible){
                mViewModel.mSearchKey.value = ""
                mViewModel.mSearchStatus.value = SearchStatus_RECORD
            }
        }
        layoutVoice.setOnClickListener {
            Toast.makeText(this,"语音",Toast.LENGTH_SHORT).show()
        }
    }

    override fun initData() {
        super.initData()
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
                    showRecordFragment()
                    setEditFocus()
                    ivDelete.visibility = View.GONE
                }
                SearchStatus_SIMILAR -> {//切换到搜索类似关键词的fragment
                    showSimilarFragment()
                    setEditFocus()
                    ivDelete.visibility = View.VISIBLE
                }
                SearchStatus_GOODS -> {//切换到搜索商品列表的fragment
                    showGoodsFragment()
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

    private fun showGoodsFragment(){
        if(mGoodsFragment==null)
            mGoodsFragment = SearchGoodsFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.layoutContent,
            mGoodsFragment!!).commit()
    }
    private fun showSimilarFragment(){
        if(mSimilarFragment==null)
            mSimilarFragment = SearchSimilarFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.layoutContent,
            mSimilarFragment!!).commit()
    }
    private fun showRecordFragment(){
        if(mRecordFragment==null)
            mRecordFragment = SearchRecordFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.layoutContent,
            mRecordFragment!!).commit()
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
        if(mGoodsFragment!=null && mGoodsFragment!!.isVisible){
            showRecordFragment()
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