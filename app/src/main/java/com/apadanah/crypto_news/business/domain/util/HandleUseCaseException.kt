package com.apadanah.crypto_news.business.domain.util

import android.annotation.SuppressLint
import android.util.Log
import com.apadanah.crypto_news.business.domain.constans.ErrorHandling


private val TAG = "AppDebug handleUseCaseException"

@SuppressLint("LongLogTag")
fun <T> handleUseCaseException(
    e: Throwable,
): DataState.Response<T> {

    Log.i(TAG, "handleUseCaseException e: " + e?.message)

    e.printStackTrace()



    e.message?.let {


        return when (it) {


            ErrorHandling.GENERAL_ERROR -> {
                DataState.Response<T>(
                    uiComponent = UIComponent.Dialog(
                        title = "",
                        description = ErrorHandling.GENERAL_ERROR
                    )
                )
            }

            else -> {
                DataState.Response<T>(
                    uiComponent = UIComponent.Dialog(
                        title = "",
                        description = ErrorHandling.GENERAL_ERROR
                    )
                )
            }


        }
    }

    return DataState.Response<T>(
        uiComponent = UIComponent.Dialog(
            title = "Error",
            description = e.message ?: "Unknown error"
        )
    )
}

