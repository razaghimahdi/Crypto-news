package com.apadanah.crypto_news.presentation.fragments.main.state

import com.apadanah.crypto_news.business.domain.model.main.SourceFilter
import com.apadanah.crypto_news.business.domain.model.main.TopicFilter
import com.apadanah.crypto_news.business.domain.util.UIComponent

sealed class MainNewsEvent {

    object GetNewsList : MainNewsEvent()

    object ResetFilter : MainNewsEvent()

    object ResetSearch : MainNewsEvent()

    data class OnUpdateInsertedTickers(val value: String) : MainNewsEvent()

    data class AddOrRemoveSelectedTickersToList(val item: String) : MainNewsEvent()

    data class OnUpdateSourceFilterList(val list: List<SourceFilter>) : MainNewsEvent()

    data class OnUpdateTopicFilterList(val list: List<TopicFilter>) : MainNewsEvent()

    data class OnUpdateFilterSortBy(val value: String) : MainNewsEvent()

    data class OnUpdateFilterType(val value: String) : MainNewsEvent()

    object GetNextPage : MainNewsEvent()

    object OnRemoveHeadFromQueue : MainNewsEvent()

    object RefreshFailedNetwork : MainNewsEvent()

    data class Error(val uiComponent: UIComponent) : MainNewsEvent()

}
