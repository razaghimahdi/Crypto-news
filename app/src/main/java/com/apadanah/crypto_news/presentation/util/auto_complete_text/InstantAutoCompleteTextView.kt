package com.apadanah.crypto_news.presentation.util.auto_complete_text

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView

class InstantAutoCompleteTextView : androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun enoughToFilter(): Boolean {
        return true
    }

    fun showDropdownNow() {
        if (adapter != null) {
            // Remember a current text
            val savedText = text

            // Set empty text and perform filtering. As the result we restore all items inside of
            // a filter's internal item collection.
            setText(null, true)

            // Set back the saved text and DO NOT perform filtering. As the result of these steps
            // we have a text shown in UI, and what is more important we have items not filtered
            setText(savedText, false)

            // Move cursor to the end of a text
            setSelection(text.length)

            // Now we can show a dropdown with full list of options not filtered by displayed text
            performFiltering(null, 0)
        }
    }
}