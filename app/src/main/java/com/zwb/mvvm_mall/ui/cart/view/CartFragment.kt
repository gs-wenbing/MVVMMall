package com.zwb.mvvm_mall.ui.cart.view

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.material.chip.ChipGroup
import com.zwb.mvvm_mall.R
import com.zwb.mvvm_mall.base.view.BaseVMFragment
import com.zwb.mvvm_mall.bean.*
import com.zwb.mvvm_mall.common.utils.Constant
import com.zwb.mvvm_mall.common.utils.StatusBarUtil
import com.zwb.mvvm_mall.common.utils.UIUtils
import com.zwb.mvvm_mall.common.view.FixedHeightBottomSheetDialog
import com.zwb.mvvm_mall.ui.cart.adapter.CartAdapter
import com.zwb.mvvm_mall.ui.cart.adapter.CartAdapter.Companion.GRID_DATA
import com.zwb.mvvm_mall.ui.cart.adapter.CartAdapter.Companion.LINEAR_DATA
import com.zwb.mvvm_mall.ui.cart.adapter.CartAdapter.Companion.STRING_DATA
import com.zwb.mvvm_mall.ui.cart.viewmodel.CartViewModel
import com.zwb.mvvm_mall.ui.goods.view.GoodsDetailActivity
import kotlinx.android.synthetic.main.dialog_cart_attr_layout.view.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.layout_cart_attr.view.*
import kotlinx.android.synthetic.main.layout_cart_toolbar.*

/**
 * 购物车
 * 数据都是模拟数据
 */
class CartFragment : BaseVMFragment<CartViewModel> (){
    //所有商品，包括购物车、推荐商品
    private var mCartMultiList:MutableList<MultiItemEntity> = ArrayList()

    private var mAllMoney:Double = 0.0
    private var mCouponMoney:Double = 32.20

    private lateinit var mAdapter: CartAdapter

    private var tempDialogGoodsModel:TextView?=null
    private var attrMap:MutableMap<String,String> = HashMap()

    override var layoutId = (R.layout.fragment_cart)

    override fun initView() {
        super.initView()
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        StatusBarUtil.darkMode(mActivity,true)

        val layoutManager = GridLayoutManager(mContext, 2)
        mAdapter = CartAdapter(mCartMultiList)
        rvCart.layoutManager = layoutManager
        rvCart.adapter = mAdapter

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val multiItem = mCartMultiList[position]
                return when {
                    STRING_DATA == multiItem.itemType -> 2//占两格
                    LINEAR_DATA == multiItem.itemType -> 2//占两格
                    GRID_DATA == multiItem.itemType -> 1//占1格
                    else -> 2
                }
            }
        }
        llCheckAll.tag = false
        llCheckAll.setOnClickListener{
            onCheckAllBoxClick()
        }

        tvCartEdit.setOnClickListener{
            onEditCart()
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.ivCheck -> onItemCheckClick(adapter.getItem(position) as CartGoodsEntity,position)
                R.id.ivIcon -> toGoodsDetail(adapter.getItem(position) as CartGoodsEntity)
                R.id.tvGoodsName -> toGoodsDetail(adapter.getItem(position) as CartGoodsEntity)
                R.id.ivReduce -> updateCartNum(adapter.getItem(position) as CartGoodsEntity,0,position)
                R.id.ivPlus -> updateCartNum(adapter.getItem(position) as CartGoodsEntity,1,position)
                R.id.tvGoodsModel -> showBottomSheetDialog(adapter.getItem(position) as CartGoodsEntity,position)
            }
        }
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val cartGoods = adapter.getItem(position) as CartLikeGoodsEntity
            if(cartGoods.itemType==GRID_DATA){
                GoodsDetailActivity.launch(requireActivity(),cartGoods.goodsName)
            }
        }
        registerDefaultLoad(rvCart,Constant.URL_CARTS)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            StatusBarUtil.darkMode(mActivity,true)
        }
    }
    override fun initData() {
        super.initData()
        mViewModel.loadCartGoodsData(Constant.URL_CARTS)
    }
    override fun initDataObserver() {
        mViewModel.mCartGoodsData.observe(this, Observer {
            it?.let {
                mCartMultiList.addAll(it)
                mAdapter.setNewData(mCartMultiList)
            }
        })
        mViewModel.mCartLikeGoodsData.observe(this, Observer {
            it?.let {
                mCartMultiList.add(CartDividingEntity(mContext.getString(R.string.cart_like)))
                mCartMultiList.addAll(it)
                mAdapter.setNewData(mCartMultiList)
            }
        })
    }

    private fun onEditCart(){
        if(tvCartEdit.text.toString() == mActivity.getString(R.string.edit)){
            tvCartEdit.text = mActivity.getString(R.string.finish)
            llAllMoney.visibility = View.GONE
            tvCartSubmit.visibility = View.GONE
            llDelete.visibility = View.VISIBLE
        }else{
            tvCartEdit.text = mActivity.getString(R.string.edit)
            llAllMoney.visibility = View.VISIBLE
            tvCartSubmit.visibility = View.VISIBLE
            llDelete.visibility = View.GONE
        }
    }
    /**
     * 跳转到商品详情
     */
    private fun toGoodsDetail(goodsEntity: CartGoodsEntity) {
        GoodsDetailActivity.launch(requireActivity(),goodsEntity.goodsName)
    }
    /**
     * 修改该商品购物车数量
     */
    private fun updateCartNum(goodsEntity: CartGoodsEntity, type: Int,position:Int) {
        mAllMoney = 0.0
        mCartMultiList.forEach {
            if (it is CartGoodsEntity ) {
                if(it.goodsID == goodsEntity.goodsID){
                    if (type == 0) {
                        if (it.cartNum > 1) {
                            it.cartNum--
                        }else{
                            return
                        }
                    } else {
                        it.cartNum++
                    }
                    it.isSelected = true
                }
                if(it.isSelected){
                    mAllMoney += it.price*it.cartNum
                }
            }
        }
        mAdapter.notifyItemChanged(position)
        setAllMoney()
    }

    /**
     * 点击购物车选择
     */
    private fun onItemCheckClick(goodsEntity: CartGoodsEntity,position:Int) {
        var isAllSelected = true
        mAllMoney = 0.0
        mCartMultiList.forEach {
            if (it is CartGoodsEntity){
                if(it.goodsID == goodsEntity.goodsID){
                    it.isSelected = !it.isSelected
                }
                if(it.isSelected){
                    mAllMoney += it.price*it.cartNum
                }else{
                    isAllSelected = false
                }
            }
        }
        mAdapter.notifyItemChanged(position)
        setAllMoney()
        //正好全部选中
        if(isAllSelected){
            ivCheckAll.setImageResource(R.mipmap.checkbox_checked)
            llCheckAll.tag = true
        }else{
            ivCheckAll.setImageResource(R.mipmap.checkbox)
            llCheckAll.tag = false
        }
    }
    /**
     * 点击全选按钮
     */
    private fun onCheckAllBoxClick(){
        mAllMoney = 0.0
        if(llCheckAll.tag as Boolean){
            ivCheckAll.setImageResource(R.mipmap.checkbox)
            llCheckAll.tag = false
            mCartMultiList.forEach {
                if (it is CartGoodsEntity)
                    it.isSelected = false
            }
            tvCoupon.visibility = View.GONE
            tvAllMoney.text = String.format(mContext.getString(R.string.cart_all_money),String.format("%.2f",mAllMoney))
        }else{
            ivCheckAll.setImageResource(R.mipmap.checkbox_checked)
            llCheckAll.tag = true
            mCartMultiList.forEach {
                if (it is CartGoodsEntity){
                    it.isSelected = true
                    mAllMoney += it.price*it.cartNum
                }
            }
            tvCoupon.visibility = View.VISIBLE
            setAllMoney()
        }
        mAdapter.notifyDataSetChanged()

    }

    /**
     * 设置总金额
     */
    private fun setAllMoney(){
        val allPrice = String.format(mContext.getString(R.string.cart_all_money),String.format("%.2f",mAllMoney))
        if(mAllMoney!=0.0){
            tvAllMoney.text = UIUtils.setTextViewContentStyle(allPrice,
                null,
                ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.mainRed)),
                3,allPrice.length
            )
        }else{
            tvAllMoney.text = allPrice
        }
        val couponPrice = String.format(mContext.getString(R.string.cart_coupon_money),String.format("%.2f",mCouponMoney))
        tvCoupon.text = UIUtils.setTextViewContentStyle(couponPrice,
            AbsoluteSizeSpan(UIUtils.dp2px(12f)),
            null,
            couponPrice.length-4,couponPrice.length
        )
    }

    /**
     * 显示选中规格的底部弹出框
     */
    private fun showBottomSheetDialog(cartGoods:CartGoodsEntity,position:Int){
        attrMap.clear()
        val dialogContainer = LayoutInflater.from(mActivity).inflate(R.layout.dialog_cart_attr_layout,null)
        cartGoods.modelList.forEach {
            val attrLayout = LayoutInflater.from(mActivity).inflate(R.layout.layout_cart_attr,null)
            attrLayout.tvAttrTitle.text = it.attrTitle
            dialogContainer.llAttrContainer.addView(attrLayout)
            setAttrTagView(attrLayout.flowLayout,it.attrList,it.attrTitle)
        }

        cartGoods.serviceList.forEach {
            val attrLayout = LayoutInflater.from(mActivity).inflate(R.layout.layout_cart_attr,null)
            attrLayout.tvAttrTitle.text = it.securityTitle
            dialogContainer.llAttrContainer.addView(attrLayout)
            setServiceTagView(attrLayout.flowLayout,it.securityList,it.securityTitle)
        }

        Glide.with(mActivity).load(cartGoods.picURL).into(dialogContainer.ivIcon)
        val price = String.format(mActivity.getString(R.string.price),cartGoods.price.toString())
        dialogContainer.tvGoodsPrice.text = UIUtils.setTextViewContentStyle(price,
            AbsoluteSizeSpan(UIUtils.dp2px(18f)),
            ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.mainRed)),
            2,price.indexOf(".")+1
        )
        dialogContainer.tvStock.text = String.format(mActivity.getString(R.string.stock),cartGoods.stock.toString())
        dialogContainer.tvGoodsModel.text = String.format(mActivity.getString(R.string.selected),cartGoods.goodsModel)
        tempDialogGoodsModel = dialogContainer.tvGoodsModel

        val bottomSheetDialog = FixedHeightBottomSheetDialog(mActivity,R.style.BottomSheetDialog,UIUtils.getScreenHeight()*5/6)
        bottomSheetDialog.setContentView(dialogContainer)
        bottomSheetDialog.show()
        dialogContainer.ivClose.setOnClickListener { bottomSheetDialog.dismiss() }
        dialogContainer.llConfirm.setOnClickListener {
            mCartMultiList.forEach {
                if (it is CartGoodsEntity && it.goodsID==cartGoods.goodsID){
                    it.goodsModel = attrMap.values.toString()
                }
            }
            mAdapter.notifyItemChanged(position)
            bottomSheetDialog.dismiss()
        }
    }


    private fun setServiceTagView(flowLayout: ChipGroup, attrList:List<SecurityEntity>, attrTitle:String) {
        val viewList:MutableList<TextView> = ArrayList()
        attrList.forEach {
            val attrName = String.format(mActivity.getString(R.string.security),it.securityLimit,it.securityPrice.toString())
            addTag2FlowLayout(flowLayout,viewList,attrName,attrTitle)
        }
    }


    private fun setAttrTagView(flowLayout: ChipGroup, attrList:List<GoodsModelItemEntity>, attrTitle:String) {
        val viewList:MutableList<TextView> = ArrayList()
        attrList.forEach {
            addTag2FlowLayout(flowLayout,viewList,it.attrName,attrTitle)
        }
    }
    /**
     * 规格标签 添加到流布局中
     */
    private fun addTag2FlowLayout(flowLayout: ChipGroup,viewList:MutableList<TextView>,attrName:String,attrTitle:String){
        val tv = TextView(mActivity)
        tv.setBackgroundResource(R.drawable.shape_grey_background)
        tv.setTextColor(ContextCompat.getColor(mContext,R.color.grey_text))
        tv.setPadding(
            UIUtils.dp2px(15f), UIUtils.dp2px(5f),
            UIUtils.dp2px(15f), UIUtils.dp2px(5f)
        )
        tv.textSize = 12f
        tv.maxLines = 1
        tv.gravity = Gravity.CENTER
        tv.text = attrName
        flowLayout.addView(tv)
        viewList.add(tv)
        tv.setOnClickListener {
            viewList.forEach {view->
                view.setBackgroundResource(R.drawable.shape_grey_background)
                view.setTextColor(ContextCompat.getColor(mContext,R.color.grey_text))
            }
            tv.setBackgroundResource(R.drawable.shape_grey_selected_background)
            tv.setTextColor(ContextCompat.getColor(mContext,R.color.mainRed))

            attrMap[attrTitle] = tv.text.toString()
            tempDialogGoodsModel?.text = String.format(mActivity.getString(R.string.selected),attrMap.values.toString())
        }
    }

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }
}