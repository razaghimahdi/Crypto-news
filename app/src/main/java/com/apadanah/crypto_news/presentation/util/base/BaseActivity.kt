package com.apadanah.crypto_news.presentation.util.base

 import android.content.Context
 import android.util.Log
 import android.view.inputmethod.InputMethodManager
 import androidx.appcompat.app.AppCompatActivity
 import com.apadanah.crypto_news.business.domain.util.ProgressBarState
 import com.apadanah.crypto_news.presentation.util.base.UICommunicationListener

abstract class BaseActivity: AppCompatActivity(),
    UICommunicationListener
{

    private val TAG: String = "AppDebug BaseActivity"




    abstract override fun displayProgressBar(progressBarState: ProgressBarState)


    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }





}









