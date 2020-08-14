package com.zwb.mvvm_mall.common.view.nested.tab

data class TabIcon(var normal:String = "",
                   var clicked:String = "")


data class DynamicTabBean(val title: String,
                        val desc: String? = "",
                        val icon: TabIcon? = null)