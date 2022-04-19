package com.zwb.module_cart.fragment

import android.os.Handler
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.material.chip.ChipGroup
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.service.goods.wrap.GoodsServiceWrap
import com.zwb.module_cart.CartApi
import com.zwb.module_cart.CartViewModel
import com.zwb.module_cart.R
import com.zwb.module_cart.adapter.CartAdapter
import com.zwb.module_cart.adapter.CartAdapter.Companion.GRID_DATA
import com.zwb.module_cart.adapter.CartAdapter.Companion.LINEAR_DATA
import com.zwb.module_cart.adapter.CartAdapter.Companion.NO_DATA
import com.zwb.module_cart.adapter.CartAdapter.Companion.STRING_DATA
import com.zwb.module_cart.baen.*
import com.zwb.module_cart.databinding.CartFragmentBinding
import com.zwb.module_cart.databinding.DialogCartAttrBinding
import com.zwb.module_cart.databinding.LayoutCartAttrBinding
import com.zwb.module_cart.view.FixedHeightBottomSheetDialog

class CartFragment: BaseFragment<CartFragmentBinding, CartViewModel>() {
    //所有商品，包括购物车、推荐商品
    private var mCartMultiList:MutableList<MultiItemEntity> = ArrayList()

    private var mAllMoney:Double = 0.0
    private var mCouponMoney:Double = 32.20

    private lateinit var mAdapter: CartAdapter

    private var tempDialogGoodsModel: TextView?=null
    private var attrMap:MutableMap<String,String> = HashMap()

    override val mViewModel by viewModels<CartViewModel>()

    override fun CartFragmentBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.setPaddingSmart(requireActivity(), mBinding.includeToolbar.toolbar)
        StatusBarUtil.darkMode(requireActivity(),true)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        mAdapter = CartAdapter(mCartMultiList)
        mBinding.rvCart.layoutManager = layoutManager
        mBinding.rvCart.adapter = mAdapter

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val multiItem = mCartMultiList[position]
                return when (multiItem.itemType) {
                    NO_DATA -> 2//占两格
                    STRING_DATA -> 2//占两格
                    LINEAR_DATA -> 2//占两格
                    GRID_DATA -> 1//占1格
                    else -> 2
                }
            }
        }
        llCheckAll.tag = false
        llCheckAll.setOnClickListener{
            onCheckAllBoxClick()
        }

        mBinding.includeToolbar.tvCartEdit.setOnClickListener{
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
                R.id.tvToCart -> Toast.makeText(requireContext(),"去逛逛",Toast.LENGTH_LONG).show()
            }
        }
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val cartGoods = adapter.getItem(position) as CartLikeGoodsEntity
            if(cartGoods.itemType==GRID_DATA){
                GoodsServiceWrap.instance.startGoodsDetail(requireActivity(),cartGoods.goodsName)
            }
        }
        setDefaultLoad(mBinding.rvCart, CartApi.CART_URL)
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            StatusBarUtil.darkMode(requireActivity(),true)
        }
    }

    override fun initRequestData() {
        //模拟加载效果
        Handler().postDelayed({
            mViewModel.loadCartGoodsData()
        }, 2000)

    }

    override fun initObserve() {
        mViewModel.mCartGoodsData.observe(viewLifecycleOwner, {
            it?.let {
                if(it.isNotEmpty()){
                    mBinding.rlCartBottom.visible()
                }else{
                    mCartMultiList.add(CartEmptyEntity(""))
                }
                mCartMultiList.addAll(it)
                mAdapter.setNewData(mCartMultiList)
            }
        })
        mViewModel.mCartLikeGoodsData.observe(viewLifecycleOwner, {
            it?.let {
                mCartMultiList.add(CartDividingEntity(getString(R.string.cart_like)))
                mCartMultiList.addAll(it)
                mAdapter.setNewData(mCartMultiList)
            }
        })
    }

    private fun onEditCart(){
        if(mBinding.includeToolbar.tvCartEdit.text.toString() == getString(R.string.edit)){
            mBinding.includeToolbar.tvCartEdit.text = getString(R.string.finish)
            mBinding.llAllMoney.visibility = View.GONE
            mBinding.tvCartSubmit.visibility = View.GONE
            mBinding.llDelete.visibility = View.VISIBLE
        }else{
            mBinding.includeToolbar.tvCartEdit.text = getString(R.string.edit)
            mBinding.llAllMoney.visibility = View.VISIBLE
            mBinding.tvCartSubmit.visibility = View.VISIBLE
            mBinding.llDelete.visibility = View.GONE
        }
    }
    /**
     * 跳转到商品详情
     */
    private fun toGoodsDetail(goodsEntity: CartGoodsEntity) {
        GoodsServiceWrap.instance.startGoodsDetail(requireActivity(),goodsEntity.goodsName)
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
            mBinding.ivCheckAll.setImageResource(R.mipmap.checkbox_checked)
            mBinding.llCheckAll.tag = true
        }else{
            mBinding.ivCheckAll.setImageResource(R.mipmap.checkbox)
            mBinding.llCheckAll.tag = false
        }
    }
    /**
     * 点击全选按钮
     */
    private fun onCheckAllBoxClick(){
        mAllMoney = 0.0
        if(mBinding.llCheckAll.tag as Boolean){
            mBinding.ivCheckAll.setImageResource(R.mipmap.checkbox)
            mBinding.llCheckAll.tag = false
            mCartMultiList.forEach {
                if (it is CartGoodsEntity)
                    it.isSelected = false
            }
            mBinding.tvCoupon.visibility = View.GONE
            mBinding.tvAllMoney.text = String.format(getString(R.string.cart_all_money),String.format("%.2f",mAllMoney))
        }else{
            mBinding.ivCheckAll.setImageResource(R.mipmap.checkbox_checked)
            mBinding.llCheckAll.tag = true
            mCartMultiList.forEach {
                if (it is CartGoodsEntity){
                    it.isSelected = true
                    mAllMoney += it.price*it.cartNum
                }
            }
            mBinding.tvCoupon.visibility = View.VISIBLE
            setAllMoney()
        }
        mAdapter.notifyDataSetChanged()

    }

    /**
     * 设置总金额
     */
    private fun setAllMoney(){
        val allPrice = String.format(getString(R.string.cart_all_money),String.format("%.2f",mAllMoney))
        if(mAllMoney!=0.0){
            mBinding.tvAllMoney.text = UIUtils.setTextViewContentStyle(allPrice,
                null,
                ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.colorPrimary)),
                3,allPrice.length
            )
        }else{
            mBinding.tvAllMoney.text = allPrice
        }
        val couponPrice = String.format(getString(R.string.cart_coupon_money),String.format("%.2f",mCouponMoney))
        mBinding.tvCoupon.text = UIUtils.setTextViewContentStyle(couponPrice,
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
        val dialogBinding = DialogCartAttrBinding.inflate(layoutInflater)
        cartGoods.modelList.forEach {
            val attrBinding = LayoutCartAttrBinding.inflate(layoutInflater)
            attrBinding.tvAttrTitle.text = it.attrTitle
            dialogBinding.llAttrContainer.addView(attrBinding.root)
            setAttrTagView(attrBinding.flowLayout,it.attrList,it.attrTitle)
        }

        cartGoods.serviceList.forEach {
            val attrBinding = LayoutCartAttrBinding.inflate(layoutInflater)
            attrBinding.tvAttrTitle.text = it.securityTitle
            dialogBinding.llAttrContainer.addView(attrBinding.root)
            setServiceTagView(attrBinding.flowLayout,it.securityList,it.securityTitle)
        }

        Glide.with(requireActivity()).load(cartGoods.picURL).into(dialogBinding.ivIcon)
        val price = String.format(getString(R.string.price),cartGoods.price.toString())
        dialogBinding.tvGoodsPrice.text = UIUtils.setTextViewContentStyle(price,
            AbsoluteSizeSpan(UIUtils.dp2px(18f)),
            ForegroundColorSpan(ContextCompat.getColor(requireActivity(),R.color.colorPrimary)),
            2,price.indexOf(".")+1
        )
        dialogBinding.tvStock.text = String.format(getString(R.string.stock),cartGoods.stock.toString())
        dialogBinding.tvGoodsModel.text = String.format(getString(R.string.selected),cartGoods.goodsModel)
        tempDialogGoodsModel = dialogBinding.tvGoodsModel

        val bottomSheetDialog = FixedHeightBottomSheetDialog(requireActivity(),R.style.BottomSheetDialog,UIUtils.getScreenHeight()*5/6)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        dialogBinding.ivClose.setOnClickListener { bottomSheetDialog.dismiss() }
        dialogBinding.llConfirm.setOnClickListener {
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
            val attrName = String.format(getString(R.string.security),it.securityLimit,it.securityPrice.toString())
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
    private fun addTag2FlowLayout(flowLayout: ChipGroup, viewList:MutableList<TextView>, attrName:String, attrTitle:String){
        val tv = TextView(requireActivity())
        tv.setBackgroundResource(R.drawable.shape_grey_background)
        tv.setTextColor(ContextCompat.getColor(requireActivity(),R.color.grey_text))
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
                view.setTextColor(ContextCompat.getColor(requireActivity(),R.color.grey_text))
            }
            tv.setBackgroundResource(R.drawable.shape_grey_selected_background)
            tv.setTextColor(ContextCompat.getColor(requireActivity(),R.color.colorPrimary))

            attrMap[attrTitle] = tv.text.toString()
            tempDialogGoodsModel?.text = String.format(getString(R.string.selected),attrMap.values.toString())
        }
    }
}