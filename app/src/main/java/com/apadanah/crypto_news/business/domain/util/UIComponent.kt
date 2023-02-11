package com.apadanah.crypto_news.business.domain.util

sealed class UIComponent {


    data class Toast(
        val description:String
    ): UIComponent()

    data class Dialog(
        val title:String,
        val description:String
    ): UIComponent()

    data class None(
        val message:String,
    ): UIComponent()



}


interface StateMessageCallback{

    fun removeMessageFromStack()
}
