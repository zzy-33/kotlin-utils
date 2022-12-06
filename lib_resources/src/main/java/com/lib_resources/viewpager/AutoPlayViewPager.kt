package com.lib_resources.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import java.lang.ref.WeakReference

/**
 * Created by Jordan on 2022/11/27.
 * 自动滚动viewpager
 */
class AutoPlayViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var adSwitchTask: AdSwitchTask? = null
    private var turning = false
    private var autoTurningTime: Long = 3000

    init {
        adSwitchTask = AdSwitchTask(this)
    }

    internal class AdSwitchTask(viewPager: AutoPlayViewPager) : Runnable {

        private val reference: WeakReference<AutoPlayViewPager> = WeakReference(viewPager)

        override fun run() {
            val mViewPager: AutoPlayViewPager? = reference.get()
            if (reference != null) {
                if (mViewPager!!.turning) {
                    mViewPager.currentItem = mViewPager.currentItem + 1
                    mViewPager.postDelayed(mViewPager.adSwitchTask, mViewPager.autoTurningTime)
                }
            }
        }
    }

    fun startTurning() {
        if (turning) {
            stopTurning()
        }
        turning = true
        postDelayed(adSwitchTask, autoTurningTime)
    }

    fun stopTurning() {
        turning = false
        removeCallbacks(adSwitchTask)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTurning()
    }

    // 退出的时候，停止滚动，回收资源
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTurning()
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            startTurning()
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            stopTurning()
        }
        return super.dispatchTouchEvent(ev)
    }

}