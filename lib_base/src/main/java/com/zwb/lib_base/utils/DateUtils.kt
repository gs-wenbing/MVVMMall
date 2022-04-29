package com.zwb.lib_base.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    /**
     * @param duration 秒钟
     */
    fun format(duration: Int): String {
        var second = ""
        var minute = ""
        var time = ""
        //获取到时间
        val mm = duration / 60 //分
        val ss = duration % 60 //秒
        second = if (ss < 10) {
            "0$ss"
        } else {
            ss.toString()
        }
        minute = if (mm < 10) {
            "0$mm"
        } else {
            mm.toString() //分钟
        }
        time = "$minute:$second"
        return time
    }

    /**
     * 按用户给的时间戳获取预设格式的时间
     *
     * @param date    时间戳
     * @param pattern 预设时间格式
     * @return
     */
    fun getDate(date: String, pattern: String?): String? {
        val dates = Date()
        dates.time = date.toLong()
        val format = SimpleDateFormat(pattern)
        return format.format(dates)
    }
}