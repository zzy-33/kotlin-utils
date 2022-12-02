package com.mobile.utillibrary.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jordan on 2022/12/2.
 */

/**
 * 格式化时间戳
 */
fun formatTimeUnix(time: Long): String {
    return if (time > 0) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.format(Date(time))
    } else {
        ""
    }
}