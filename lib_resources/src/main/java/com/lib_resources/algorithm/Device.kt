package com.lib_resources.algorithm

import android.content.Context
import android.os.Build
import android.provider.Settings

/**
 * Created by Jordan on 2022/12/3.
 */
object Device {

    // 设备名称
    val name = Build.PRODUCT.toString()

    // 设备号
    fun getAndroidId(context: Context): String {
        return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            .toString()
    }

}