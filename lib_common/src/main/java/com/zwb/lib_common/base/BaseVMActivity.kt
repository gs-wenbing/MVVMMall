package com.zwb.lib_common.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.State
import com.zwb.lib_base.net.StateType
import com.zwb.lib_common.callback.EmptyCallback
import com.zwb.lib_common.callback.ErrorCallback
import com.zwb.lib_common.callback.PlaceHolderCallback
import com.zwb.lib_common.view.LoadingDialog

abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB, VM>() {

    private var loadMap: HashMap<String, LoadService<*>> = HashMap()
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = LoadingDialog(this, false)
    }

    fun setPlaceHolderLoad(view: View, resId:Int, key: String) {
        val loadSir = LoadSir.Builder()
            .addCallback(PlaceHolderCallback(resId))
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            .setDefaultCallback(PlaceHolderCallback::class.java)
            .build()
        val loadService = loadSir.register(view) {
            initRequestData()
        }
        loadMap[key] = loadService
    }
    fun setDefaultLoad(view: View, key: String) {
        val loadService = LoadSir.getDefault().register(view) {
            initRequestData()
        }
        loadMap[key] = loadService!!
    }

    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(this, false)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }

    private fun showSuccess(key: String) {
        loadMap.remove(key)?.showCallback(SuccessCallback::class.java)
    }

    private fun showEmpty(key: String) {
        loadMap.remove(key)?.showCallback(EmptyCallback::class.java)
    }

    private fun showError(key: String) {
        loadMap.remove(key)?.showCallback(ErrorCallback::class.java)
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess(it.urlKey)
                    StateType.ERROR -> showError(it.urlKey)
                    StateType.NETWORK_ERROR -> showError(it.urlKey)
                    StateType.EMPTY -> showEmpty(it.urlKey)
                    else -> showSuccess(it.urlKey)
                }
            }
        }
    }
}