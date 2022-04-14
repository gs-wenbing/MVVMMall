package com.zwb.lib_base.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.zwb.lib_base.utils.ActivityStackManager
import com.zwb.lib_base.utils.LogUtils

/**
 * Activity生命周期监听
 *
 * @author Qu Yunshuo
 * @since 4/20/21 9:10 AM
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    private val TAG = "ActivityLifecycle"

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        ActivityStackManager.addActivityToStack(activity)
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivityCreated")
    }

    override fun onActivityStarted(activity: Activity) {
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivityStarted")
    }

    override fun onActivityResumed(activity: Activity) {
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivityResumed")
    }

    override fun onActivityPaused(activity: Activity) {
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivityPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivityStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityStackManager.popActivityToStack(activity)
        LogUtils.e(TAG, "${activity.javaClass.simpleName} --> onActivityDestroyed")
    }
}