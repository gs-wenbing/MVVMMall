package com.zwb.lib_base.mvvm.v

import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.State
import com.zwb.lib_base.net.StateType
import com.zwb.lib_base.utils.*
import com.zwb.lib_base.utils.callback.EmptyCallback
import com.zwb.lib_base.utils.callback.ErrorCallback
import com.zwb.lib_base.utils.callback.PlaceHolderCallback
import com.zwb.lib_base.utils.network.AutoRegisterNetListener
import com.zwb.lib_base.utils.network.NetworkStateChangeListener
import com.zwb.lib_base.utils.network.NetworkTypeEnum
import com.zwb.lib_base.view.LoadingDialog
import me.jessyan.autosize.AutoSizeCompat

/**
 * Activity基类
 *
 * @author Qu Yunshuo
 * @since 8/27/20
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
    FrameView<VB>, NetworkStateChangeListener {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    protected abstract val mViewModel: VM

    private var loadMap: HashMap<String, LoadService<*>> = HashMap()
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(mBinding.root)
        ARouter.getInstance().inject(this)

        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)

        mLoadingDialog = LoadingDialog(this, false)
        mViewModel.loadState.observe(this, observer)

        setStatusBar()
        mBinding.initView()
        initNetworkListener()
        initObserve()
        initRequestData()
    }
    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "ActivityStack: ${ActivityStackManager.activityStack}")
    }
    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        lifecycle.addObserver(AutoRegisterNetListener(this))
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    open fun setStatusBar() {
//        BarUtils.transparentStatusBar(this)
//        BarUtils.setStatusBarLightMode(this, true)
    }

    /**
     * 网络类型更改回调
     * @param type Int 网络类型
     * @return Unit
     */
    override fun networkTypeChange(type: NetworkTypeEnum) {}

    /**
     * 网络连接状态更改回调
     * @param isConnected Boolean 是否已连接
     * @return Unit
     */
    override fun networkConnectChange(isConnected: Boolean) {
        Toast.makeText(this,if (isConnected) "网络已连接" else "网络已断开", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
    }

    fun setPlaceHolderLoad(view: View, resId:Int, key: String) {
        if(loadMap[key] == null){
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
    }
    fun setDefaultLoad(view: View, key: String) {
        if(loadMap[key] == null){
            val loadService = LoadSir.getDefault().register(view) {
                initRequestData()
            }
            loadMap[key] = loadService!!
        }
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

    override fun getResources(): Resources {
        // 主要是为了解决 AndroidAutoSize 在横屏切换时导致适配失效的问题
        // 但是 AutoSizeCompat.autoConvertDensity() 对线程做了判断 导致Coil等图片加载框架在子线程访问的时候会异常
        // 所以在这里加了线程的判断 如果是非主线程 就取消单独的适配
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        }
        return super.getResources()
    }
}