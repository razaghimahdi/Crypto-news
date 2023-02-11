package com.apadanah.crypto_news.presentation.util.custom_view

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.apadanah.crypto_news.R
import java.util.*

fun Context.GetFont(): Typeface? {
    val currentLang: String = Locale.getDefault().language
    return when (currentLang) {
        else -> ResourcesCompat.getFont(this, R.font.font_medium)
    }
}