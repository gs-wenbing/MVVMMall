package com.zwb.mvvm_mall.base.view

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zwb.mvvm_mall.MyApplication
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import com.zwb.mvvm_mall.base.vm.BaseViewModel
import com.zwb.mvvm_mall.common.callback.EmptyCallBack
import com.zwb.mvvm_mall.common.callback.ErrorCallBack
import com.zwb.mvvm_mall.common.callback.LoadingCallBack
import com.zwb.mvvm_mall.common.callback.PlaceHolderCallBack
import com.zwb.mvvm_mall.common.utils.CommonUtils
import com.zwb.mvvm_mall.common.utils.Constant.COMMON_KEY

abstract class BaseVMFragment<VM : BaseViewModel<*>> : BaseFragment(),BaseView {

    protected lateinit var mViewModel: VM
    private var loadService: LoadService<*>? = null
    private var loadKeys:MutableList<String> = ArrayList()

    override fun setContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val rootView = inflater.inflate(layoutId, container, false)
        mViewModel = getActivityViewModelProvider(mActivity).get(CommonUtils.getClass(this))
        return rootView
    }

    override fun initView() {
        super.initView()
        mViewModel.loadState.observe(viewLifecycleOwner, observer)
        initDataObserver()
    }

    /**
     * 注册 PlaceHolderView 默认显示PlaceHolderView
     * 数据加载成功需要调用 showSuccess(Constant.COMMON_KEY) 来显示原来的页面
     */
    fun registerPlaceHolderLoad(view: View,placeHolderLayoutID:Int){
       val loadSir = LoadSir.Builder()
            .addCallback(PlaceHolderCallBack(placeHolderLayoutID))
            .addCallback(EmptyCallBack())
            .addCallback(ErrorCallBack())
            .setDefaultCallback(PlaceHolderCallBack::class.java)
            .build()
        loadService =  loadSir.register(view) {  reLoad() }
    }

    override fun registerDefaultLoad(view: View, key:String){
        if(!TextUtils.isEmpty(key))loadKeys.add(key)
        loadService =  LoadSir.getDefault().register(view) {  reLoad() }
    }

    override fun initData() {
        if(loadKeys.size>0)
            showLoading(loadKeys[0])
    }

    open fun initDataObserver() {}

    /**
     * 显示PlaceHolderView
     */
    fun showPlaceHolder() {
        loadService?.showCallback(PlaceHolderCallBack::class.java)
    }

    /**
     * 成功回调
     */
    override fun showSuccess(key:String) {
        if(loadKeys.contains(key) || COMMON_KEY == key){
            loadService?.showCallback(SuccessCallback::class.java)
        }
    }

    override fun showLoading(key:String) {
        if(loadKeys.contains(key) || COMMON_KEY == key){
            loadService?.showCallback(LoadingCallBack::class.java)
        }
    }

    override fun showEmpty(key:String) {
        if(loadKeys.contains(key) || COMMON_KEY == key){
            loadService?.showCallback(EmptyCallBack::class.java)
        }
    }

    override fun showError(msg: String,key:String) {
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
        }
        if(loadKeys.contains(key) || COMMON_KEY == key){
            loadService?.showCallback(ErrorCallBack::class.java)
        }
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess(it.urlKey)
                    StateType.ERROR -> showError("网络异常",it.urlKey)
                    StateType.NETWORK_ERROR -> showError("网络异常",it.urlKey)
                    StateType.EMPTY -> showEmpty(it.urlKey)
                    else -> showEmpty(it.urlKey)
                }
            }
        }
    }
}