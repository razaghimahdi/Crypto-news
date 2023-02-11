package com.apadanah.crypto_news.presentation.util.dialogs

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.apadanah.crypto_news.R

fun Activity.customDialog(layout: Int, isCancelable: Boolean): Dialog {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(layout)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialog.setCancelable(isCancelable)
    return dialog
}

fun Activity.dialogError(callback: () -> Unit) {

    val dialog = customDialog(layout = R.layout.dialog_network_error, true)
    dialog.show()
    val btnOk = dialog.findViewById<View>(R.id.btnOk) as Button
    btnOk.setOnClickListener {
        dialog.cancel()
        callback()
    }


}

fun Activity.informationDialog() {

    val dialog = customDialog(layout = R.layout.dialog_information, true)
    dialog.show()
    val btnOk = dialog.findViewById<Button>(R.id.btnOk)
    btnOk.setOnClickListener {
        dialog.cancel()
    }


}