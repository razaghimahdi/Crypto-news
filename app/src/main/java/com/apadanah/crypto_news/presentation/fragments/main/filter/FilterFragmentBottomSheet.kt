package com.apadanah.crypto_news.presentation.fragments.main.filter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.constans.Constants.FILTER_SORT_NEWS_By_OLDEST
import com.apadanah.crypto_news.business.domain.constans.Constants.FILTER_SORT_NEWS_By_RANK
import com.apadanah.crypto_news.business.domain.constans.Constants.FILTER_TYPE_NEWS_By_ARTICLE
import com.apadanah.crypto_news.business.domain.constans.Constants.FILTER_TYPE_NEWS_By_VIDEO
import com.apadanah.crypto_news.business.domain.model.main.SOURCE_FILTER_LIST
import com.apadanah.crypto_news.business.domain.model.main.SourceFilter
import com.apadanah.crypto_news.business.domain.model.main.TOPIC_FILTER_LIST
import com.apadanah.crypto_news.business.domain.model.main.TopicFilter
import com.apadanah.crypto_news.databinding.FilterFragmentBottomSheetBinding
import com.apadanah.crypto_news.presentation.fragments.main.state.MainNewsEvent
import com.apadanah.crypto_news.presentation.fragments.main.state.MainNewsState
import com.apadanah.crypto_news.presentation.util.bottom_sheet.showBottomSheetDialog


private val TAG = "AppDebug filterFragmentBottomSheet"

fun Fragment.filterFragmentBottomSheet(
    state: MainNewsState,
    events: (MainNewsEvent) -> Unit,
) {
    val binding = FilterFragmentBottomSheetBinding.inflate(
        LayoutInflater.from(context)
    )
    val bottomSheet = this.showBottomSheetDialog(binding)

    var selectedTopicList = arrayListOf<TopicFilter>()
    selectedTopicList.addAll(state.topic)
    var selectedSourceList = arrayListOf<SourceFilter>()
    selectedSourceList.addAll(state.source)


    // init some view
    binding.txtSourceSelectedSort.text = "(${getString(R.string.sort_by)} ${getString(R.string.newest)})"

    requireActivity().showCountOfSelectedList(
        selectedTopicList,
        binding.txtSourceSelectedCountTopic
    )
    requireActivity().showCountOfSelectedList(
        selectedSourceList,
        binding.txtSourceSelectedCountSource
    )


    // add chips list

    SOURCE_FILTER_LIST.forEach { source ->
        binding.chipsGroupSource?.addView(
            createSourceChip(
                requireContext(),
                source,
                state.source
            ) { source, _ ->
                selectedSourceList = addOrRemoveToSourceFilterList(
                    selectedSourceFilter = source,
                    selectedList = selectedSourceList
                )
                requireActivity().showCountOfSelectedList(
                    selectedSourceList,
                    binding.txtSourceSelectedCountSource
                )

            } as View)
    }

    TOPIC_FILTER_LIST.forEach { topic ->
        binding.chipsGroupTopic?.addView(
            createTopicChip(
                requireContext(),
                topic,
                state.topic
            ) { topic, _ ->
                selectedTopicList = addOrRemoveToTopicFilterList(
                    selectedTopicFilter = topic,
                    selectedList = selectedTopicList
                )
                requireActivity().showCountOfSelectedList(
                    selectedTopicList,
                    binding.txtSourceSelectedCountTopic
                )

            } as View)
    }


    // check update state

    when (state.type) {
        FILTER_TYPE_NEWS_By_VIDEO -> {
            binding.chipsVideo?.isChecked = true
        }
        FILTER_TYPE_NEWS_By_ARTICLE -> {
            binding.chipsArticle?.isChecked = true
        }
    }

    when (state.sortBy) {
        FILTER_SORT_NEWS_By_RANK -> {
            binding.radioButtonRank?.isChecked = true
        }
        FILTER_SORT_NEWS_By_OLDEST -> {
            binding.radioButtonOldest?.isChecked = true
        }
        else -> {
            binding.radioButtonNewest?.isChecked = true
        }
    }


    // listen to change listener

    binding.radioGroupSort?.setOnCheckedChangeListener { group, checkedId ->

        when (checkedId) {
            R.id.radioButtonNewest -> {
                binding.txtSourceSelectedSort?.text = "(${getString(R.string.sort_by)} ${getString(R.string.newest)})"
            }
            R.id.radioButtonOldest -> {
                binding.txtSourceSelectedSort?.text = "(${getString(R.string.sort_by)} ${getString(R.string.oldest)})"
            }
            R.id.radioButtonRank -> {
                binding.txtSourceSelectedSort?.text = "(${getString(R.string.sort_by)} ${getString(R.string.rank)})"
            }
        }
    }

    binding.chipsGroupType?.setOnCheckedChangeListener { group, checkedId ->
        when (checkedId) {
            R.id.chipsVideo -> {
                binding.txtSourceSelectedType?.text =
                    "(${getString(R.string.type)}: ${getString(R.string.video)})"
            }
            R.id.chipsArticle -> {
                binding.txtSourceSelectedType?.text =
                    "(${getString(R.string.type)}: ${getString(R.string.article)})"
            }
            else -> {
                binding.txtSourceSelectedType?.text = ""
            }
        }
    }


    // onclick to show list by animation

    binding.relativeSource?.setOnClickListener {
        expandOrCollapseView(
            arrow = binding.imgViewArrowSource!!,
            expandView = binding.linearExpandSource!!
        )
    }

    binding.relativeType?.setOnClickListener {
        expandOrCollapseView(
            arrow = binding.imgViewArrowType!!,
            expandView = binding.linearExpandType!!
        )
    }

    binding.relativeSort?.setOnClickListener {
        expandOrCollapseView(
            arrow = binding.imgViewArrowSort!!,
            expandView = binding.linearExpandSort!!
        )
    }

    binding.relativeTopic?.setOnClickListener {
        expandOrCollapseView(
            arrow = binding.imgViewArrowTopic!!,
            expandView = binding.linearExpandTopic!!
        )
    }


    binding.btnReset?.setOnClickListener {
        events(MainNewsEvent.ResetFilter)
        events(MainNewsEvent.GetNewsList)
        bottomSheet.dismiss()
    }

    binding.btnApply?.setOnClickListener {

        events(
            MainNewsEvent.OnUpdateFilterSortBy(
                binding.radioGroupSort?.checkedRadioButtonId?.getSortFromRadioGroup() ?: ""
            )
        )

        events(
            MainNewsEvent.OnUpdateFilterType(
                binding.chipsGroupType?.checkedChipId?.getTypeFromChipsGroup() ?: ""
            )
        )

        events(MainNewsEvent.OnUpdateSourceFilterList(selectedSourceList))

        events(MainNewsEvent.OnUpdateTopicFilterList(selectedTopicList))

        events(MainNewsEvent.GetNewsList)

        bottomSheet.dismiss()
    }

    bottomSheet.show()
}


