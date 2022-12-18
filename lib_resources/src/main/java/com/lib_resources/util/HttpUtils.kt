package com.lib_resources.util

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL

/**
 * Created by Jordan on 2022/12/8.
 */
object HttpUtils {

    const val TAG = "HttpUtils"

    /**
     * http 网络请求
     * @param url URL
     */
    fun loadJson(url: URL) {
        try {
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "*/*")
            connection.connect()
            // 结果码
            val respone = connection.responseCode
            if (respone == HTTP_OK) {
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val line = bufferedReader.readLine()
                Log.d(TAG, "line:${line}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
