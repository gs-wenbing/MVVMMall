package com.zwb.lib_base.utils.callback

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback

class PlaceHolderCallback(var layoutID:Int) :Callback(){

    override fun onCreateView(): Int = layoutID

    override fun onReloadEvent(context: Context?, view: View?): Boolean = true


}