package com.lib_resources.util

import android.content.Context
import android.content.Intent

/**
 * Created by Jordan on 2022/12/20.
 */
object Broadcast {

    /**
     * 开机自动启动
     */
    fun <T> onBootCompleted(context: Context, intent: Intent, cls: Class<T>) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val intent = Intent(context, cls)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
