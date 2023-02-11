package com.apadanah.crypto_news.business.domain.util

sealed class ProgressBarState{

    object Loading: ProgressBarState()

    object Idle: ProgressBarState()

}