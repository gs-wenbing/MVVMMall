package com.zwb.lib_base.mvvm.v

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.utils.BindingReflex
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.EventBusUtils
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