package com.mobile.utillibrary.time

import java.util.*

/**
 * Created by Jordan on 2022/11/22.
 * 定时任务TimeTask
 * 在固定时间内完成任务
 */
class HeartbeatTimer {

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var mListener: OnScheduleListener? = null

    init {
        timer = Timer()
    }

    /**
     * 开启定时任务
     * @param delay Long 多少秒后开始
     * @param period Long   间隔多少秒
     */
    fun startTimer(delay: Long, period: Long) {
        timerTask = object : TimerTask() {
            override fun run() {
                mListener?.onSchedule()
            }
        }
        timer?.schedule(timerTask, delay, period)
    }

    fun cancel() {
        timerTask?.cancel()
        timer?.cancel()
    }

    interface OnScheduleListener {
        fun onSchedule()
    }

    fun onScheduleListener(onnScheduleListener: OnScheduleListener) {
        mListener = onnScheduleListener
    }
}