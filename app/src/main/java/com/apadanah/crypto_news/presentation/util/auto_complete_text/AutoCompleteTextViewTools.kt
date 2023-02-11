package com.apadanah.crypto_news.presentation.util.auto_complete_text

import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

object AutoCompleteTextViewTools {

    fun getPositionSelectedOfAutoCompleteTexView(input:String, list:List<String>):Int{
        val itemFromList=list.find { it==input }
        return list.indexOf(itemFromList)
    }

    fun getValueOfAutoCompleteTexViewPosition(position:Int, list:List<String>):String{
        if (position> -1){

            return list[position].toString().trim()
        }

        return ""

    }
    fun AutoCompleteTextView.showDropdown(adapter: ArrayAdapter<String>?) {
        if(!TextUtils.isEmpty(this.text.toString())){
            adapter?.filter?.filter(null)
        }
    }
}