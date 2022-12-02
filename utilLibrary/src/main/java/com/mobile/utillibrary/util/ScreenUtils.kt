package com.mobile.utillibrary.util

import android.content.Context
import android.content.res.Resources

/**
 * Created by Jordan on 2022/11/23.
 */

fun Number.dp2px(context: Context? = null): Int {
    val resources = context?.resources ?: Resources.getSystem()
    val scale: Float = resources.displayMetrics.density
    return (this.toDouble() * scale + 0.5f).toInt()
}

fun Number.dp2pxF(context: Context? = null): Float {
    val resources = context?.resources ?: Resources.getSystem()
    val scale: Float = resources.displayMetrics.density
    return (this.toDouble() * scale).toFloat()
}

fun Number.px2dp(context: Context? = null): Int {
    val resources = context?.resources ?: Resources.getSystem()
    return (this.toDouble() / resources.displayMetrics.density + 0.5f).toInt()
}

fun Number.px2dpF(context: Context? = null): Float {
    val resources = context?.resources ?: Resources.getSystem()
    return (this.toDouble() / resources.displayMetrics.density).toFloat()
}
