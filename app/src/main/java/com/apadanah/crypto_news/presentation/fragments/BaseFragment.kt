package com.apadanah.crypto_news.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.apadanah.crypto_news.presentation.util.base.UICommunicationListener

abstract class BaseFragment : Fragment(){

    private val TAG: String = "AppDebug BaseAdsFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    @SuppressLint("LongLogTag")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            uiCommunicationListener = context as UICommunicationListener
        }catch(e: ClassCastException){
            Log.e(TAG, "$context must implement UICommunicationListener" )
        }

    }
}