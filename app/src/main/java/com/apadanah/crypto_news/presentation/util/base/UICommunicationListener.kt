package com.apadanah.crypto_news.presentation.util.base

import com.apadanah.crypto_news.business.domain.util.ProgressBarState

interface UICommunicationListener {

    fun displayProgressBar(progressBarState: ProgressBarState)

    fun hideSoftKeyboard()

}