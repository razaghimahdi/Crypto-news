package com.apadanah.crypto_news.presentation.util.custom_view

import android.content.Context
import android.util.AttributeSet 
import com.google.android.material.button.MaterialButton

class CustomButton : MaterialButton {

    constructor(context: Context?) : super(context!!){init()}

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs){init()}

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ){init()}


    fun init(){
        if (!isInEditMode){
            val tf = context.GetFont()
            this.typeface = tf;
        }
    }
}