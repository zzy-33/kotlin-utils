package com.mobile.utillibrary.util

import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.WebView

/**
 * Created by Jordan on 2022/11/13.
 */

/**
 * 防止WebView内存泄漏
 * @param webview
 */
fun clearWebView(web: WebView) {
    if (web != null) {
        val parent: ViewParent = web.getParent()
        if (parent != null) {
            (parent as ViewGroup).removeView(web)
            web.destroy()
        }
        web.stopLoading()
        web.settings.javaScriptEnabled = false
        web.clearHistory();
        web.clearView();
        web.removeAllViews();
        web.destroy();
    }
}
