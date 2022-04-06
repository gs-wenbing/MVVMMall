package com.zwb.module_home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.zwb.module_home.R
import com.zwb.module_home.adapter.MenuGridAdapter
import kotlinx.android.synthetic.main.home_fragment_menu_grid.*

class MenuGridFragment : Fragment(R.layout.home_fragment_menu_grid) {

    var page : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menu_recyclerview.layoutManager = GridLayoutManager(activity, 5)
        menu_recyclerview.adapter = MenuGridAdapter(requireActivity(), page)
    }
}