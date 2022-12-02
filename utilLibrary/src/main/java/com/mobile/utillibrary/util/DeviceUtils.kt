package com.mobile.utillibrary.util

import android.content.Context
import android.os.Build
import android.provider.Settings

/**
 * Created by Jordan on 2022/12/2.
 */

/**
 * 获取Android ID
 */
fun getAndroidId(context: Context): String {
    return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        .toString()
}

/**
 * 获取设备名称
 */
fun getDeviceName(): String {
    return Build.PRODUCT
}