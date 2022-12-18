package com.lib_resources.util

import android.graphics.BitmapFactory

/**
 * Created by Jordan on 2022/12/13.
 */

fun calculateInSampleSize(options: BitmapFactory.Options, maxWidth: Int, maxHeight: Int): Int {
    //这里其实是获取到默认的高度和宽度，也就是图片的实际高度和宽度
    val height = options.outHeight
    val width = options.outWidth
    //默认采样率为1，也就是不变嘛。
    var inSampleSize = 1

    //===============核心算法啦====================
    if (width > maxWidth || height > maxHeight) {
        inSampleSize = if (width > height) {
            Math.round(height.toFloat() / maxHeight.toFloat())
        } else {
            Math.round(width.toFloat() / maxWidth.toFloat())
        }
        val totalPixels = (width * height).toFloat()
        val maxTotalPixels = (maxWidth * maxHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
            inSampleSize++
        }
    }
    //=============核心算法end================
    return inSampleSize
}