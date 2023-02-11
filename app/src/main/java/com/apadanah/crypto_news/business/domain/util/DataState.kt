package com.apadanah.crypto_news.business.domain.util


sealed class DataState<T> {

    data class Response<T>(val uiComponent: UIComponent): DataState<T>()

    data class Data<T>(val data:T?=null): DataState<T>()

    data class Loading<T>(val progressBarState: ProgressBarState = ProgressBarState.Idle):
        DataState<T>()

}