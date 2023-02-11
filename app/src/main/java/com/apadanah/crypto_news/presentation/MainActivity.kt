package com.apadanah.crypto_news.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.databinding.ActivityMainBinding
import com.apadanah.crypto_news.presentation.util.base.BaseActivity

class MainActivity : BaseActivity() {

    val TAG = "AppDebug MainActivity"
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun displayProgressBar(progressBarState: ProgressBarState) {
        if (progressBarState == ProgressBarState.Loading) {
            binding.progressBarCenter.visibility = View.VISIBLE
        } else {
            binding.progressBarCenter.visibility = View.GONE
        }
    }

}