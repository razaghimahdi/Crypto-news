package com.apadanah.crypto_news.presentation.util.animation

import android.view.View
import android.widget.ImageView


fun toggleArrow(view: View, rotateTo: Float): Boolean {
    return if (view.rotation == 0f) {
        view.animate().setDuration(200).rotation(rotateTo)
        true
    } else {
        view.animate().setDuration(200).rotation(0f)
        false
    }
}


fun toggleImageSrc(view: ImageView,  drawable: Int) {
    view.setImageResource(drawable)
}

