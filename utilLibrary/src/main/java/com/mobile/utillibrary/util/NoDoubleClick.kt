package com.mobile.utillibrary.util

import android.view.View
import com.mobile.utillibrary.R

/**
 * Created by Jordan on 2022/11/13.
 * 防抖点击
 * 在固定时间内只响应一次点击事件
 * @param delay int
 * @param black (T)->unit
 */
private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(R.id.triggerLastTimeKey) != null) getTag(R.id.triggerLastTimeKey) as Long else 0
    set(value) = setTag(R.id.triggerLastTimeKey, value)

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(R.id.triggerDelayKey) != null) getTag(R.id.triggerDelayKey) as Long else 0
    set(value) = setTag(R.id.triggerDelayKey, value)

private fun <T : View> T.clickEnable(): Boolean {
    var clickable = false
    var currentTime = System.currentTimeMillis()
    if (currentTime - triggerLastTime >= triggerDelay) {
        clickable = true
    }
    triggerLastTime = currentTime
    return clickable
}

//点击调用
fun <T : View> T.clickWithTrigger(delay: Long = 600, block: (T) -> Unit) {
    triggerDelay = delay
    setOnClickListener {
        if (clickEnable()) {
            block(this)
        }
    }
}

