package com.apadanah.crypto_news.presentation.fragments.main.state

import com.apadanah.crypto_news.business.domain.constans.Constants.FILTER_SORT_NEWS_By_NEWEST
import com.apadanah.crypto_news.business.domain.model.main.News
import com.apadanah.crypto_news.business.domain.model.main.SourceFilter
import com.apadanah.crypto_news.business.domain.model.main.TICKERS_LIST
import com.apadanah.crypto_news.business.domain.model.main.TopicFilter
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.business.domain.util.Queue
import com.apadanah.crypto_news.business.domain.util.UIComponent

data class MainNewsState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val isNetworkFailed: Boolean = false,
    val newsList: List<News> = listOf(),
    val selectedTickers: List<String> = listOf(),
    val suggestTickers: List<String> = TICKERS_LIST,
    val insertedTickers: String = "",
    val topic: List<TopicFilter> = listOf(),
    val source: List<SourceFilter> = listOf(),
    val type: String = "",
    val sortBy: String = FILTER_SORT_NEWS_By_NEWEST,
    val page: Int = 1,
    var isPagingAvailable: Boolean = true, // no more results available, prevent next page
    // val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
