package com.zwb.module_goods.view

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.getScreenHeight
import com.zwb.lib_base.ktx.getScreenWidth
import com.zwb.module_goods.R
import com.zwb.module_goods.bean.GoodsAttrFilterEntity
import com.zwb.module_goods.databinding.PopupListBinding


class PopupWindowList(var activity:FragmentActivity): PopupWindow(){
    private lateinit var mAdapter:PopupAdapter
    //Popup弹出后 mDimView变暗
    private var mDimView: ViewGroup? = null
    private lateinit var mBinding: PopupListBinding
    private var mOnPopupListener: OnPopupListener? = null

    init {
        // 设置可以获得焦点
        isFocusable = true
        // 设置弹窗内可点击
        isTouchable = true
        // 设置弹窗外可点击
        isOutsideTouchable = true
        // 设置弹窗的宽度和高度
        width = activity.getScreenWidth()
        height = activity.getScreenHeight()/3
        initView()
    }

    private fun initView() {
        mBinding = PopupListBinding.inflate(activity.layoutInflater)
        contentView = mBinding.root
        mBinding.recyclerview.layoutManager = GridLayoutManager(activity,2)
        mAdapter = PopupAdapter(null)
        mBinding.recyclerview.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val goodsAttrFilter = adapter.data[position] as GoodsAttrFilterEntity
            goodsAttrFilter.isSelected = !goodsAttrFilter.isSelected
            adapter.notifyDataSetChanged()
        }
        mBinding.tvReset.setOnClickListener {
            mOnPopupListener?.onReset()
        }
        mBinding.tvConfirm.setOnClickListener {
            val selects = ArrayList<GoodsAttrFilterEntity>()
            mAdapter.data.forEach {
                if(it.isSelected){
                    selects.add(it)
                }
            }
            mOnPopupListener?.onConfirm(selects)

        }
    }

    fun setData(list:List<GoodsAttrFilterEntity>){
        mAdapter.setNewData(list)
        mAdapter.notifyDataSetChanged()
    }
    private var isConfirm: Boolean = false

    fun dismiss(isConfirm: Boolean){
        this.isConfirm = isConfirm
        dismiss()
    }

    override fun dismiss() {
        super.dismiss()
        mOnPopupListener?.onDismiss(isConfirm)
        clearDim()
    }
    fun updateItem(isConfirm:Boolean) {
        if(!isConfirm){
            mAdapter.data.forEach {
                it.isSelected = false
                mAdapter.notifyDataSetChanged()
            }
        }
    }
    /**
     * Popup弹出后 dimView变暗
     */
    fun applyDim(dimAmount: Float,dimView: ViewGroup?) {
        mDimView = dimView
        mDimView?.let {
            val dim = ColorDrawable(Color.BLACK)
            dim.setBounds(0, 0, it.width, it.height)
            dim.alpha = (255 * dimAmount).toInt()
            val overlay = it.overlay
            overlay.add(dim)
        }
    }

    /**
     * 变暗恢复
     */
    fun clearDim() {
        mDimView?.let {
            val overlay = it.overlay
            overlay.clear()
        }
    }
    fun getPopupAdapter():PopupAdapter = mAdapter

    fun setOnPopupListener(listener: OnPopupListener) {
        mOnPopupListener = listener
    }
    class PopupAdapter(data: MutableList<GoodsAttrFilterEntity>?) :
        BaseQuickAdapter<GoodsAttrFilterEntity, BaseViewHolder>(R.layout.item_goodslist_attr_filter2, data) {
        override fun convert(helper: BaseViewHolder, item: GoodsAttrFilterEntity?) {
            item?.let {
                helper.setText(R.id.tvAttrName,it.attrName)
                if(it.isSelected){
                    helper.setGone(R.id.ivAttrSelected,true)
                    helper.setTypeface(R.id.tvAttrName, Typeface.DEFAULT_BOLD)
                }else{
                    helper.setGone(R.id.ivAttrSelected,false)
                    helper.setTypeface(R.id.tvAttrName, Typeface.DEFAULT)
                }
            }

        }
    }

    interface OnPopupListener {
        fun onConfirm(selects:List<GoodsAttrFilterEntity>)
        fun onReset()
        fun onDismiss(isConfirm: Boolean)
    }
}