package com.apadanah.crypto_news.presentation.fragments.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.presentation.fragments.BaseFragment
import com.apadanah.crypto_news.presentation.util.toast.MyToastSnackBar
import com.apadanah.crypto_news.databinding.DetailsNewsFragmentBinding

@SuppressLint("LongLogTag")
class DetailNewsFragment : BaseFragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private val TAG = "AppDebug DetailNewsFragment"


    private var _binding: DetailsNewsFragmentBinding? = null
    private val binding get() = _binding!!

    var news_url = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkIfUrlIsEmpty()

        news_url = arguments?.getString("news_url") ?: ""

        binding.swipeRefresh.setOnRefreshListener(this)
        uiCommunicationListener.displayProgressBar(ProgressBarState.Loading)
        initWebview()

        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }


    private fun checkIfUrlIsEmpty() {
        if (news_url.isNotEmpty()) {
            MyToastSnackBar.simpleToastFloating(
                requireActivity(),
                getString(R.string.something_wrong)
            )
            findNavController().popBackStack()
        }
    }

    private fun initWebview() {


        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.setSupportZoom(false)
            /*
               settings.javaScriptCanOpenWindowsAutomatically = true
               settings.allowFileAccess = true
               isVerticalScrollBarEnabled = true
               isHorizontalScrollBarEnabled = false
               settings.domStorageEnabled = true*/
            webViewClient = MyWebViewClient(
                onPageStartedCallback = {
                    uiCommunicationListener.displayProgressBar(ProgressBarState.Loading)
                },
                onPageFinished = {
                    uiCommunicationListener.displayProgressBar(ProgressBarState.Idle)
                },
                onReceivedError = {
                    // i'm not sure we need this for now, but let this be here maybe we use it one day
                    /* updateFailedNetworkStatus(isFailed = false) {
                         refreshWebView()
                     }*/
                    MyToastSnackBar.simpleToastFloating(
                        requireActivity(),
                        getString(R.string.something_wrong)
                    )
                },
            )


            loadUrl(news_url)
        }
    }

    override fun onRefresh() {
        binding.webView.loadUrl(news_url)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onPause() {
        super.onPause()
        // in case maybe we need to deActive loading by myself
        uiCommunicationListener.displayProgressBar(ProgressBarState.Idle)
    }


}