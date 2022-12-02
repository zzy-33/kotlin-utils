package com.mobile.utillibrary.algorithm

import android.util.Base64

/**
 * Created by Jordan on 2022/11/30.
 */
object Base64Utils {
    fun encodeBase64(bytes: ByteArray): ByteArray {
        return Base64.encode(bytes, Base64.DEFAULT);
    }

    fun decodeBase64(key: String): ByteArray {
        return Base64.decode(key, Base64.DEFAULT);
    }

    fun enCodeString(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
    }
}