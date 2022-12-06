package com.lib_resources.animation

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.lib_resources.R


/**
 * Created by Jordan on 2022/12/2.
 */

class LoadingView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var img: ImageView? = null
    private var animationSet: AnimationSet? = null

    init {
        initView()
    }

    private fun initView() {
        img = ImageView(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        img!!.layoutParams = params
        img!!.setImageResource(R.drawable.loading)
        addView(img)
        initLoading()
    }

    //加载动画
    private fun initLoading() {
        animationSet = AnimationSet(true)
        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.repeatCount = -1
        rotate.startOffset = 0
        rotate.duration = 1000
        animationSet?.interpolator = LinearInterpolator()
        animationSet?.addAnimation(rotate)
    }

    fun start() {
        if (img != null && animationSet != null) {
            img!!.startAnimation(animationSet)
        }
    }

    fun stop() {
        if (animationSet != null) {
            animationSet!!.cancel()
        }
        animationSet = null
        if (img != null) {
            img!!.clearAnimation()
        }
    }
}