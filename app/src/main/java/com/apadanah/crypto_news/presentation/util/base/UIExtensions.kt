package com.apadanah.crypto_news.presentation.util.base

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.apadanah.crypto_news.business.domain.constans.ErrorHandling
import com.apadanah.crypto_news.business.domain.util.Queue
import com.apadanah.crypto_news.business.domain.util.StateMessageCallback
import com.apadanah.crypto_news.business.domain.util.UIComponent
import com.apadanah.crypto_news.presentation.util.dialogs.dialogError
import com.apadanah.crypto_news.presentation.util.toast.MyToastSnackBar

private val TAG: String = "AppDebug"

fun processQueue(
    context: Activity?,
    queue: Queue<UIComponent>,
    stateMessageCallback: StateMessageCallback
) {
    context?.let { ctx ->
        if (!queue.isEmpty()) {
            queue.peek()?.let { uiComponent ->

                when (uiComponent) {
                    is UIComponent.Dialog -> {
                        ctx.CreateUIComponentDialog(
                            title = uiComponent.title,
                            description = uiComponent.description,
                            stateMessageCallback = stateMessageCallback
                        )
                    }
                    is UIComponent.Toast -> {
                        ctx.CreateUIComponentToast(
                            description = uiComponent.description,
                            stateMessageCallback = stateMessageCallback
                        )
                    }
                    else -> {
                        Log.i(TAG, "processQueue else: ")
                    }
                }


            }
        }
    }
}


private fun Activity.CreateUIComponentDialog(
    title: String,
    description: String,
    stateMessageCallback: StateMessageCallback
) {
    when (description) {
        ErrorHandling.GENERAL_ERROR -> {
            this.dialogError { stateMessageCallback.removeMessageFromStack() }
        }
        else -> {
            // TODO(make general dialog)
        }
    }
}


private fun Activity.CreateUIComponentToast(
    description: String,
    stateMessageCallback: StateMessageCallback
) {
    MyToastSnackBar.simpleToastFloating(this, description)
    stateMessageCallback.removeMessageFromStack()
}




