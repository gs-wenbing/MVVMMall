package com.zwb.mvvm_mall

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.kingja.loadsir.core.LoadSir
import com.zwb.mvvm_mall.common.callback.EmptyCallBack
import com.zwb.mvvm_mall.common.callback.ErrorCallBack
import com.zwb.mvvm_mall.common.callback.LoadingCallBack

class MyApplication : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        lateinit var INSTANCE: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        mAppViewModelStore = ViewModelStore()
        LoadSir.beginBuilder()
            .addCallback(LoadingCallBack())
            .addCallback(ErrorCallBack())
            .addCallback(EmptyCallBack())
//            .setDefaultCallback(LoadingCallBack::class.java)
            .commit()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        return ViewModelProvider(
            activity.applicationContext as MyApplication,
            (activity.applicationContext as MyApplication).getAppFactory(activity)
        )
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory as ViewModelProvider.Factory
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException("Your activity/fragment is not yet attached to " + "Application. You can't request ViewModel before onCreate call.")
    }
}