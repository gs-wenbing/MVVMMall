package com.zwb.lib_base.mvvm.v

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.State
import com.zwb.lib_base.net.StateType
import com.zwb.lib_base.utils.BindingReflex
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.callback.EmptyCallback
import com.zwb.lib_base.utils.callback.ErrorCallback
import com.zwb.lib_base.utils.callback.PlaceHolderCallback
import com.zwb.lib_base.view.LoadingDialog
import kotlin.system.measureTimeMillis

/**
 * Fragment基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(),
    FrameView<VB> {

    lateinit var mContext: Context
    /**
     * 私有的 ViewBinding 此写法来自 Google Android 官方
     */
    private var _binding: VB? = null

    protected val mBinding get() = _binding!!

    protected abstract val mViewModel: VM

    private var loadMap: HashMap<String, LoadService<*>> = HashMap()
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLoadingDialog = LoadingDialog(view.context, false)
        mViewModel.loadState.observe(viewLifecycleOwner, observer)

        val aRouterTimes = measureTimeMillis {
            // ARouter 依赖注入
            ARouter.getInstance().inject(this)
        }
        Log.d("$javaClass==fragment", "init: ARouter : $aRouterTimes ms")

        val eventBusTimes = measureTimeMillis {
            // 注册EventBus
            if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)
        }
        Log.d("$javaClass==fragment", "init: EventBus : $eventBusTimes ms")

        val initViewTimes = measureTimeMillis {
            _binding?.initView()
        }
        Log.d("$javaClass==fragment", "init: initView : $initViewTimes ms")


        val initDataTimes = measureTimeMillis {
            initObserve()
            initRequestData()
        }
        Log.d("$javaClass==fragment", "init: initData : $initDataTimes ms")


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
        mLoadingDialog.showDialog(mContext, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
    }
}