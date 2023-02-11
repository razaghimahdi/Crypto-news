package com.apadanah.crypto_news.presentation.fragments.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apadanah.crypto_news.BuildConfig
import com.apadanah.crypto_news.business.domain.constans.Constants
import com.apadanah.crypto_news.business.domain.constans.ErrorHandling.GENERAL_ERROR
import com.apadanah.crypto_news.business.domain.model.main.News
import com.apadanah.crypto_news.business.domain.model.main.SourceFilter
import com.apadanah.crypto_news.business.domain.model.main.TICKERS_LIST
import com.apadanah.crypto_news.business.domain.model.main.TopicFilter
import com.apadanah.crypto_news.business.domain.util.DataState
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.business.domain.util.Queue
import com.apadanah.crypto_news.business.domain.util.UIComponent
import com.apadanah.crypto_news.business.interactors.main.GetNews
import com.apadanah.crypto_news.presentation.fragments.main.state.MainNewsEvent
import com.apadanah.crypto_news.presentation.fragments.main.state.MainNewsState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("LongLogTag")
class MainNewsViewModel
constructor(
    private val getNews: GetNews
) : ViewModel() {

    private val TAG = "AppDebug MainNewsViewModel"

    val state: MutableLiveData<MainNewsState> = MutableLiveData(MainNewsState())

    val stateQueueUIComponent: MutableLiveData<Queue<UIComponent>> =
        MutableLiveData(Queue(mutableListOf()))


    fun onTriggerEvent(event: MainNewsEvent) {
        when (event) {

            is MainNewsEvent.OnUpdateInsertedTickers -> {
                onUpdateInsertedTickers(value = event.value)
            }

            is MainNewsEvent.AddOrRemoveSelectedTickersToList -> {
                addOrRemoveSelectedTickersToList(item = event.item)
            }

            is MainNewsEvent.OnUpdateFilterSortBy -> {
                onUpdateFilterSortBy(value = event.value)
            }

            is MainNewsEvent.OnUpdateFilterType -> {
                onUpdateFilterType(value = event.value)
            }

            is MainNewsEvent.OnUpdateSourceFilterList -> {
                onUpdateSourceFilterList(list = event.list)
            }

            is MainNewsEvent.OnUpdateTopicFilterList -> {
                onUpdateTopicFilterList(list = event.list)
            }

            is MainNewsEvent.GetNewsList -> {
                getNewsList()
            }

            is MainNewsEvent.ResetFilter -> {
                resetFilter()
            }

            is MainNewsEvent.ResetSearch -> {
                resetSearch()
            }

            is MainNewsEvent.GetNextPage -> {
                getNextPage()
            }

            is MainNewsEvent.RefreshFailedNetwork -> {
                refreshFailedNetwork()
            }

            is MainNewsEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is MainNewsEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

        }
    }


    init {
        onTriggerEvent(MainNewsEvent.GetNewsList)
    }

    private fun addOrRemoveSelectedTickersToList(item: String) {
        addOrRemoveSuggestTickersToList(item)
        val currentList = arrayListOf<String>()
        currentList.addAll(state.value?.selectedTickers ?: listOf())
        if (!currentList.contains(item)) {
            currentList.add(item)
        } else {
            currentList.remove(item)
        }
        this.state.value = state.value?.copy(selectedTickers = currentList)

    }
    private fun addOrRemoveSuggestTickersToList(item: String) {
        val currentList = arrayListOf<String>()
        currentList.addAll(state.value?.suggestTickers ?: listOf())
        if (!currentList.contains(item)) {
            currentList.add(item)
        } else {
            currentList.remove(item)
        }
        this.state.value = state.value?.copy(suggestTickers = currentList)

    }

    private fun onUpdateInsertedTickers(value: String) {
        this.state.value = state.value?.copy(insertedTickers = value)
    }

    private fun onUpdateTopicFilterList(list: List<TopicFilter>) {
        this.state.value = state.value?.copy(topic = list)
    }

    private fun onUpdateSourceFilterList(list: List<SourceFilter>) {
        this.state.value = state.value?.copy(source = list)
    }

    private fun onUpdateFilterSortBy(value: String) {
        this.state.value = state.value?.copy(sortBy = value)
    }

    private fun onUpdateFilterType(value: String) {
        this.state.value = state.value?.copy(type = value)
    }

    private fun resetFilter() {
        state.value = state.value?.copy(topic = listOf())
        state.value = state.value?.copy(newsList = listOf())
        state.value = state.value?.copy(topic = listOf())
        state.value = state.value?.copy(source = listOf())
        state.value = state.value?.copy(type = "")
        state.value = state.value?.copy(sortBy = Constants.FILTER_SORT_NEWS_By_NEWEST)
    }

    private fun resetSearch() {
        state.value = state.value?.copy(selectedTickers = listOf())
        state.value = state.value?.copy(suggestTickers = TICKERS_LIST)
        state.value = state.value?.copy(insertedTickers = "")
    }

    private fun getNextPage() {

        incrementPage()

        getNews.execute(
            token = BuildConfig.API_TOKEN,
            page = state.value!!.page,
            tickers = state.value!!.selectedTickers,
            topic = state.value!!.topic,
            source = state.value!!.source,
            insertedTickers = state.value!!.insertedTickers,
            type = state.value!!.type,
            sortBy = state.value!!.sortBy,
        ).onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    onTriggerEvent(MainNewsEvent.Error(dataState.uiComponent))
                }
                is DataState.Data -> {
                    dataState.data?.let {
                        addNewListToCurrentList(list = it)
                    }
                }
                is DataState.Loading -> {
                    updateProgressBarState(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getNewsList() {

        // refresh state
        this.state.value = state.value?.copy(isPagingAvailable = true)
        this.state.value = state.value?.copy(page = 1)
        this.state.value = state.value?.copy(isNetworkFailed = false)

        getNews.execute(
            token = BuildConfig.API_TOKEN,
            page = state.value!!.page,
            tickers = state.value!!.selectedTickers,
            topic = state.value!!.topic,
            source = state.value!!.source,
            insertedTickers = state.value!!.insertedTickers,
            type = state.value!!.type,
            sortBy = state.value!!.sortBy,
        ).onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    Log.i(TAG, "getNewsList dataState.uiComponent: " + dataState.uiComponent)
                    onTriggerEvent(MainNewsEvent.Error(dataState.uiComponent))
                    if (dataState.uiComponent is UIComponent.Dialog) { // show failed network
                        handleResponse(dataState.uiComponent.description)
                    }
                }
                is DataState.Data -> {
                    dataState.data?.let {
                        this.state.value = state.value?.copy(newsList = it)
                    }
                }
                is DataState.Loading -> {
                    updateProgressBarState(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addNewListToCurrentList(list: List<News>) {
        if (list.isEmpty()) {
            this.state.value = state.value?.copy(isPagingAvailable = false)
            return
        }
        val newList = state.value?.newsList as ArrayList
        newList.addAll(list)
        this.state.value = state.value?.copy(newsList = newList)
    }

    private fun incrementPage() {
        this.state.value = state.value?.copy(page = state.value?.page!! + 1)
    }

    private fun networkFailed() {
        state.value = state.value?.copy(isNetworkFailed = true)
    }

    private fun refreshFailedNetwork() {
        onTriggerEvent(MainNewsEvent.ResetFilter)
        onTriggerEvent(MainNewsEvent.GetNewsList)
    }

    private fun handleResponse(message: String) {
        when (message) {
            GENERAL_ERROR -> {
                networkFailed()
            }
        }
    }


    private fun updateProgressBarState(progressBarState: ProgressBarState) {
        // this@MainNewsViewModel.stateProgressBarState.value = progressBarState
        this.state.value = state.value?.copy(progressBarState = progressBarState)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        if (uiComponent is UIComponent.None) {
            Log.i(TAG, "onTriggerEvent:  ${(uiComponent as UIComponent.None).message}")
            return
        }

        stateQueueUIComponent.value?.let { state ->
            val queue = state
            queue.add(uiComponent)
            this@MainNewsViewModel.stateQueueUIComponent.value =
                Queue(mutableListOf()) // force reload
            this@MainNewsViewModel.stateQueueUIComponent.value = queue
        }
        /* state.value?.let { state ->
             val queue = state.errorQueue
             queue.add(uiComponent)
             this@MainNewsViewModel.state.value =
                 state.copy(errorQueue = Queue(mutableListOf())) // force reload
             this@MainNewsViewModel.state.value = state.copy(errorQueue = queue)
         }*/
    }

    private fun removeHeadMessage() {
        stateQueueUIComponent.value?.let { state ->
            try {
                val queue = state
                queue.remove() // can throw exception if empty
                this@MainNewsViewModel.stateQueueUIComponent.value =
                    Queue(mutableListOf())// force reload
                this@MainNewsViewModel.stateQueueUIComponent.value = queue
            } catch (e: Exception) {
                Log.i(TAG, "removeHeadMessage: Nothing to remove from DialogQueue")
            }
        }


        /*   state.value?.let { state ->
               try {
                   val queue = state.errorQueue
                   queue.remove() // can throw exception if empty
                   this@MainNewsViewModel.state.value =
                       state.copy(errorQueue = Queue(mutableListOf())) // force reload
                   this@MainNewsViewModel.state.value = state.copy(errorQueue = queue)
               } catch (e: Exception) {
                   Log.i(TAG, "removeHeadMessage: Nothing to remove from DialogQueue")
               }
           }
           */
    }

}