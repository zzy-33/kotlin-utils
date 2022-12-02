package com.mobile.utillibrary.textView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Choreographer
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.mobile.utillibrary.R
import com.mobile.utillibrary.dp2pxF

/**
 * Created by Jordan on 2022/11/23.
 * 思路
 * 我们只要将单行的TextView截成一张Bitmap，然后我们再自定义一个View，重写它的onDraw方法，
 * 每隔一段时间，将这张Bitmap画在不同的坐标上（左右两边各draw一次），这样连续起来看起来就是走马灯效果了
 */
class MarqueeTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    companion object {
        /**
         * Unit: PX
         */
        private const val DEFAULT_SPACE = 100

        /**
         * Unit: DP
         */
        private const val DEFAULT_SPEED = 0.5f
        private const val BASE_FPS = 60f
    }

    private var mFps = 60
    private var mTextView: TextView? = null
    private var mBitmap: Bitmap? = null
    private val mFrameCallback: Choreographer.FrameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            mLeftX -= BASE_FPS / mFps * mSpeed
            invalidate()
            Choreographer.getInstance().postFrameCallback(this)
        }
    }
    private var mLeftX = 0f

    /**
     * 文字滚动时，头尾的最小间隔距离
     */
    private var mSpace: Int = DEFAULT_SPACE

    /**
     * 文字滚动速度
     */
    private var mSpeed: Float = DEFAULT_SPEED * 2

    init {
        initView(attrs)
    }

    @SuppressLint("LongLogTag")
    private fun initView(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView)
            mSpace =
                typedArray.getDimensionPixelSize(R.styleable.MarqueeTextView_space, DEFAULT_SPACE)
            val speedDp = typedArray.getFloat(R.styleable.MarqueeTextView_speed, DEFAULT_SPEED)
            mSpeed = speedDp.dp2pxF(context)
            typedArray.recycle()
        } else {
            mSpeed = DEFAULT_SPEED.dp2pxF(context)
        }
        mTextView = TextView(context, attrs)
        mTextView!!.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        mTextView!!.maxLines = 1
        maxLines = 1
        mTextView!!.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            updateBitmap()
            restartScroll()
        }
    }

    override fun setText(text: CharSequence, type: BufferType) {
        super.setText(text, type)
        //执行父类构造函数时，如果AttributeSet中有text参数会先调用setText，此时mTextView尚未初始化
        if (mTextView != null) {
            mTextView!!.text = text
            requestLayout()
        }
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        //执行父类构造函数时，如果AttributeSet中有textSize参数会先调用setTextSize，此时mTextView尚未初始化
        if (mTextView != null) {
            mTextView!!.textSize = size
            requestLayout()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mTextView!!.measure(MeasureSpec.UNSPECIFIED, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mTextView!!.layout(left, top, left + mTextView!!.measuredWidth, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        if (mBitmap != null) {
            if (mTextView!!.measuredWidth <= width) {
                var space = mSpace - (width - mTextView!!.measuredWidth)
                if (space < 0) {
                    space = 0
                }
                if (mLeftX < -width - space) {
                    mLeftX += (width + space).toFloat()
                }
                canvas.drawBitmap(mBitmap!!, mLeftX, 0f, paint)
                if (mLeftX < 0) {
                    canvas.drawBitmap(mBitmap!!, width + mLeftX + space, 0f, paint)
                }
            } else {
                if (mLeftX < -mTextView!!.measuredWidth - mSpace) {
                    mLeftX += (mTextView!!.measuredWidth + mSpace).toFloat()
                }
                canvas.drawBitmap(mBitmap!!, mLeftX, 0f, paint)
                if (mLeftX + (mTextView!!.measuredWidth - width) < 0) {
                    canvas.drawBitmap(
                        mBitmap!!, mTextView!!.measuredWidth + mLeftX + mSpace, 0f,
                        paint
                    )
                }
            }
        }
    }

    private fun updateBitmap() {
        mBitmap =
            Bitmap.createBitmap(mTextView!!.measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = mBitmap?.let { Canvas(it) }
        mTextView!!.draw(canvas)
    }

    private fun updateFps() {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mFps = windowManager.defaultDisplay.refreshRate.toInt()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Choreographer.getInstance().removeFrameCallback(mFrameCallback)
    }

    fun startScroll() {
        updateFps()
        Choreographer.getInstance().postFrameCallback(mFrameCallback)
    }

    fun pauseScroll() {
        Choreographer.getInstance().removeFrameCallback(mFrameCallback)
    }

    fun stopScroll() {
        mLeftX = 0f
        Choreographer.getInstance().removeFrameCallback(mFrameCallback)
    }

    fun restartScroll() {
        stopScroll()
        startScroll()
    }
}
