package com.mobile.utillibrary.viewpager

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.mobile.utillibrary.dp2px

/**
 * Created by Jordan on 2022/11/27.
 */
class LooperPage(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private lateinit var viewPager: AutoPlayViewPager
    private lateinit var pointContainer: LinearLayout
    private var mInnerAdapter: InnerAdapter? = null

    init {
        initAttrs(attrs)
        initView()
    }


    private fun initAttrs(attrs: AttributeSet) {

    }

    private fun initView() {
        viewPager = AutoPlayViewPager(context, null)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        viewPager.layoutParams = params
        addView(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //  切换的一个回调方法
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {

            }

            // 切换停下的回调
            override fun onPageSelected(position: Int) {
                updateIndicator()
            }

            // 切换状态改变的回调
            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    fun setData(innerAdapter: InnerAdapter) {
        viewPager.adapter = innerAdapter
        viewPager.currentItem = Integer.MAX_VALUE / 2 + 1
        mInnerAdapter = innerAdapter
        updateIndicator()
    }

    private fun updateIndicator() {
        if (mInnerAdapter != null) {
            pointContainer.removeAllViews()
            val count = mInnerAdapter!!.getDataSize()
            for (item in 0 until count) {
                val point = View(context)
                if (viewPager.currentItem % count == item) {
                    point.setBackgroundColor(Color.RED)
                } else {
                    point.setBackgroundColor(Color.WHITE)
                }
                val params = LayoutParams(5.dp2px(context), 5.dp2px(context))
                params.setMargins(5.dp2px(context), 0, 5.dp2px(context), 0)
                point.layoutParams = params
                pointContainer.addView(point)
            }
        }
    }


    abstract class InnerAdapter : PagerAdapter() {

        protected abstract fun getSubView(container: ViewGroup, position: Int): View
        abstract fun getDataSize(): Int

        override fun getCount(): Int {
            return Integer.MAX_VALUE
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val resPosition = position % getDataSize()
            val itemView = getSubView(container, resPosition)
            container.addView(itemView)
            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }


}