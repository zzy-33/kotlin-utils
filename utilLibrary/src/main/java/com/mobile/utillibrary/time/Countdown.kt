package com.mobile.utillibrary.time

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.CountDownTimer
import android.widget.Button
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference


/**
 * Created by Jordan on 2022/11/13.
 * 实现倒计时功能
 * @param Button
 * 注意：在页面销毁时一定要调用cancel()方法
 */
class Countdown(tvCode: Button) {
    var tvCodeWr: WeakReference<Button>? = null  //控件软引用，防止内存泄漏
    private var timer: CountDownTimer? = null

    init {
        this.tvCodeWr = WeakReference(tvCode)
    }

    //这是倒计时执行方法
    fun runTimer(
        millisInFuture: Long,
        onFinishText: Int,
        onFinishTextColor: Int,
        onFinishBackground: Int,
        onTickBackground: Int
    ) {
        timer = object : CountDownTimer(millisInFuture + 100, 1000) {
            override fun onFinish() {
                tvCodeWr?.get()?.let {
                    it.setText(onFinishText)
                    it.setTextColor(ContextCompat.getColor(it.context, onFinishTextColor))
                    it.setBackgroundResource(onFinishBackground)
                    it.isClickable = true
                    it.isEnabled = true
                }
                cancle()
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                tvCodeWr?.get()?.let {
                    it.isClickable = false
                    it.isEnabled = false
                    it.setTextColor(Color.parseColor("#FF999999"))
                    it.setBackgroundResource(onTickBackground)
                    it.text = "${millisUntilFinished / 1000}s"
                }
            }

        }.start();
    }

    //这个方法可以在activity或者fragment销毁的时候调用，防止内存泄漏
    fun cancle() {
        timer?.cancel()
        timer = null
    }
}