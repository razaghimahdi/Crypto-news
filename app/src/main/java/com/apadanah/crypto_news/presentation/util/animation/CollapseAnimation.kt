package com.apadanah.crypto_news.presentation.util.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


fun collapse(v: View) {
    val initialHeight = v.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
            } else {
                v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = ((initialHeight / v.context.resources.displayMetrics.density) / 2).toLong()
    v.startAnimation(a)
}