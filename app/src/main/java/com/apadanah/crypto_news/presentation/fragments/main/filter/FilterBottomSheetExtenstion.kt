package com.apadanah.crypto_news.presentation.fragments.main.filter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.constans.Constants
import com.apadanah.crypto_news.business.domain.model.main.SOURCE_FILTER_LIST
import com.apadanah.crypto_news.business.domain.model.main.SourceFilter
import com.apadanah.crypto_news.business.domain.model.main.TOPIC_FILTER_LIST
import com.apadanah.crypto_news.business.domain.model.main.TopicFilter
import com.apadanah.crypto_news.presentation.util.animation.AnimListener
import com.apadanah.crypto_news.presentation.util.animation.collapse
import com.apadanah.crypto_news.presentation.util.animation.expand
import com.apadanah.crypto_news.presentation.util.animation.toggleArrow
import com.apadanah.crypto_news.presentation.util.custom_view.CustomChip
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable



fun Int.getSortFromRadioGroup(): String {
    return when (this) {
        R.id.radioButtonNewest -> {
            Constants.FILTER_SORT_NEWS_By_NEWEST
        }
        R.id.radioButtonOldest -> {
            Constants.FILTER_SORT_NEWS_By_OLDEST
        }
        R.id.radioButtonRank -> {
            Constants.FILTER_SORT_NEWS_By_RANK
        }
        else -> {
            ""
        }
    }
}

fun Int.getTypeFromChipsGroup(): String {
    return when (this) {
        R.id.chipsVideo -> {
            Constants.FILTER_TYPE_NEWS_By_VIDEO
        }
        R.id.chipsArticle -> {
            Constants.FILTER_TYPE_NEWS_By_ARTICLE
        }
        else -> {
            ""
        }
    }
}


fun expandOrCollapseView(arrow: View, expandView: View) {
    val show: Boolean = toggleArrow(arrow, 90f)
    if (show) {
        expand(expandView, object : AnimListener {
            override fun onFinish() {
                // scroll to top, not now
            }
        })
    } else {
        collapse(expandView)
    }
}


fun createSourceChip(
    context: Context,
    sourceFilter: SourceFilter,
    selectedList: List<SourceFilter>,
    callBackOnCheckedChangeListener: (SourceFilter, Boolean) -> Unit,
): CustomChip {
    return CustomChip(context).apply {
        text = sourceFilter.name
        id = SOURCE_FILTER_LIST.indexOf(sourceFilter)
        val chipDrawable = ChipDrawable.createFromAttributes(
            context,
            null,
            0,
            R.style.Chip_Choice_Selectable
        )
        this.setEnsureMinTouchTargetSize(false)
        this.setChipDrawable(chipDrawable)
        this.setTextColor(resources.getColor(R.color.grey_500))

        if (selectedList.contains(sourceFilter)) {
            this.isChecked = true
        }

        this.setOnCheckedChangeListener { compoundButton, b ->
            callBackOnCheckedChangeListener(sourceFilter, b)
            if (b) {
                this.setTextColor(resources.getColor(R.color.white))
            } else {
                this.setTextColor(resources.getColor(R.color.grey_500))
            }
        }
    }

}

fun createTopicChip(
    context: Context, topicFilter: TopicFilter,
    selectedList: List<TopicFilter>,
    callBackOnCheckedChangeListener: (TopicFilter, Boolean) -> Unit
): CustomChip {
    return CustomChip(context).apply {
        text = topicFilter.name
        id = TOPIC_FILTER_LIST.indexOf(topicFilter)
        val chipDrawable = ChipDrawable.createFromAttributes(
            context,
            null,
            0,
            R.style.Chip_Choice_Selectable
        )
        this.setEnsureMinTouchTargetSize(false)
        this.setChipDrawable(chipDrawable)
        this.setTextColor(resources.getColor(R.color.grey_500))

        if (selectedList.contains(topicFilter)) {
            this.isChecked = true
        }

        this.setOnCheckedChangeListener { compoundButton, b ->
            callBackOnCheckedChangeListener(topicFilter, b)
            if (b) {
                this.setTextColor(resources.getColor(R.color.white))
            } else {
                this.setTextColor(resources.getColor(R.color.grey_500))
            }

        }
    }

}


fun addOrRemoveToTopicFilterList(
    selectedTopicFilter: TopicFilter,
    selectedList: ArrayList<TopicFilter>
): ArrayList<TopicFilter> {

    if (!selectedList.contains(selectedTopicFilter)) {
        selectedList.add(selectedTopicFilter)
    } else {
        selectedList.remove(selectedTopicFilter)
    }

    return selectedList
}

fun addOrRemoveToSourceFilterList(
    selectedSourceFilter: SourceFilter,
    selectedList: ArrayList<SourceFilter>
): ArrayList<SourceFilter> {
    if (!selectedList.contains(selectedSourceFilter)) {
        selectedList.add(selectedSourceFilter)
    } else {
        selectedList.remove(selectedSourceFilter)
    }
    return selectedList
}

fun Context.showCountOfSelectedList(list: ArrayList<*>, textView: TextView?) {

    if (list.isNotEmpty()) {
        textView?.text =
            "(${list.size} ${getString(R.string.item_selected)})"
    } else {
        textView?.text = ""
    }
}




