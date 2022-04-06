package com.zwb.mvvm_mall

import android.content.Intent
import android.util.SparseArray
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.module_home.R
import com.zwb.mvvm_mall.databinding.ActivityHomeTabBinding

class HomeTabActivity:BaseActivity<ActivityHomeTabBinding,SplashViewModel>() {

    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()
    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    override val mViewModel by viewModels<SplashViewModel>()

    override fun ActivityHomeTabBinding.initView() {
        switchFragment(HOME)
        initBottomNavigation()
    }
    private fun initBottomNavigation() {
        mBinding.navView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    switchFragment(HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_classify -> {
                    switchFragment(CLASSIFY)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_cart -> {
                    switchFragment(CART)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_mine -> {
                    switchFragment(MINE)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                HOME -> fragment = ARouter.getInstance().build("/home/HomeFragment").navigation() as Fragment
                CLASSIFY -> fragment = ARouter.getInstance().build("/classify/ClassifyFragment").navigation() as Fragment
                CART -> fragment = ARouter.getInstance().build("/cart/cartFragment").navigation() as Fragment
                MINE -> fragment = ARouter.getInstance().build("/my/MyFragment").navigation() as Fragment
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
    }
    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    companion object {
        const val HOME = 0
        const val CLASSIFY = 1
        const val CART = 2
        const val MINE = 3
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, HomeTabActivity::class.java)
                startActivity(intent)
            }
    }
}
