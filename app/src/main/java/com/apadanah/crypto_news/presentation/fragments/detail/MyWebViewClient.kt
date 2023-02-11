package com.apadanah.crypto_news.presentation.fragments.detail

import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.constans.Constants
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.presentation.util.toast.MyToastSnackBar


class MyWebViewClient(
   val onPageStartedCallback: () -> Unit,
   val onPageFinished: () -> Unit,
   val onReceivedError: () -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        // i'm not sure we need this for now, but let this be here maybe we use it one day
        // viewModel.updateURL(url)
        view.loadUrl(url)

        return true
    }

    override fun onReceivedError(
        view: WebView,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        onReceivedError()
        view.loadUrl(Constants.BLANK_URL)
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler,
        error: SslError?
    ) {
        super.onReceivedSslError(view, handler, error)
        handler.cancel()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onPageStartedCallback()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageFinished()
    }
}
