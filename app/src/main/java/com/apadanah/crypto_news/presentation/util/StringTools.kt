package com.apadanah.crypto_news.presentation.util

object StringTools {

    fun List<String>.toStringWithComma(): String {
        val separator = ","
        return this.joinToString(separator).removeWhitespaces()
    }

    fun String.removeWhitespaces() = replace(" ", "")


}