package com.apadanah.crypto_news.business.interactors.main

import android.util.Log
import com.apadanah.crypto_news.business.datasource.network.main.MainNewsApiService
import com.apadanah.crypto_news.business.datasource.network.main.responses.toNews
import com.apadanah.crypto_news.business.domain.constans.Constants.ALL_TICKERS_PARAMETER
import com.apadanah.crypto_news.business.domain.constans.Constants.CATEGORY_URL
import com.apadanah.crypto_news.business.domain.model.main.News
import com.apadanah.crypto_news.business.domain.model.main.SourceFilter
import com.apadanah.crypto_news.business.domain.model.main.TopicFilter
import com.apadanah.crypto_news.business.domain.util.DataState
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.business.domain.util.handleUseCaseException
import com.apadanah.crypto_news.presentation.util.StringTools.toStringWithComma
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetNews(
    private val service: MainNewsApiService
) {

    private val TAG: String = "AppDebug GetNews"


    fun execute(
        token: String,
        page: Int = 1,
       tickers: List<String>,
        insertedTickers: String,
        topic: List<TopicFilter>,
        source: List<SourceFilter>,
        type: String,
        sortBy: String,
    ): Flow<DataState<List<News>>> = flow {
        try {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))


            val newTickers = arrayListOf<String>()
            newTickers.addAll(tickers)
            if (insertedTickers.isNotEmpty()) newTickers.add(insertedTickers)


            val sourceValue = arrayListOf<String>()
            source.forEach {
                sourceValue.add(it.value)
            }
            val topicValue = arrayListOf<String>()
            topic.forEach {
                topicValue.add(it.value)
            }

            val requestBody: MutableMap<String, String> = HashMap()

            // if tickers is empty then we gonna send section in /category
            val url = if (newTickers.isEmpty()) CATEGORY_URL else ""

            // convert tickers to string
            val tickersParams = if (newTickers.isNotEmpty()) newTickers.toStringWithComma() else ""

            // convert topic to string
            val topicParams = if (topicValue.isNotEmpty()) topicValue.toStringWithComma() else ""

            // convert source to string
            val sourceParams = if (sourceValue.isNotEmpty()) sourceValue.toStringWithComma() else ""


            // add parameter to request body

            if (topicParams.isNotEmpty()) requestBody["topic"] = topicParams

            if (sourceParams.isNotEmpty()) requestBody["source"] = sourceParams

            if (type.isNotEmpty()) requestBody["type"] = type

            if (sortBy.isNotEmpty()) requestBody["sortby"] = sortBy

            if (tickersParams.isNotEmpty()) {
                requestBody["tickers"] = tickersParams
            } else {
                requestBody["section"] = ALL_TICKERS_PARAMETER
            }


            Log.i(TAG, "execute page: " + page)
            Log.i(TAG, "execute token: " + token)
            Log.i(TAG, "execute url: " + url)
            Log.i(TAG, "execute tickersParams: " + tickersParams)
            Log.i(TAG, "execute topicParams: " + topicParams)
            Log.i(TAG, "execute sourceParams: " + sourceParams)
            Log.i(TAG, "execute type: " + type)
            Log.i(TAG, "execute sortBy: " + sortBy)


            val response =
                service.getAllNewsByFilterAPI(
                    url = url,
                    token = token,
                    page = page,
                    fields = requestBody
                )


            val list = response.data.map { it.toNews() }

            Log.i(TAG, "execute list size: "+list.size)
            Log.i(TAG, "execute list: "+list)

            // emit(handleUseCaseSuccessfulResponse<List<News>>(SUCCESS_DONE))

            emit(DataState.Data(list))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(handleUseCaseException<List<News>>(e))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }


}