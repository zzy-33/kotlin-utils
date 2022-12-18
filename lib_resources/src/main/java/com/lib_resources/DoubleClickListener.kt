package com.lib_resources

import android.view.View

/**
 * Created by Jordan on 2022/12/18.
 * 双击监听
 */
abstract class DoubleClickListener : View.OnClickListener {

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300
    }

    var lastClickTime: Long = 0

    abstract fun onDoubleClick(v: View?)

    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(v)
        }
        lastClickTime = clickTime
    }

}