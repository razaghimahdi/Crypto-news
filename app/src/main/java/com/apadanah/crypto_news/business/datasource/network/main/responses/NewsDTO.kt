package com.apadanah.crypto_news.business.datasource.network.main.responses

import com.apadanah.crypto_news.business.domain.model.main.News
import com.google.gson.annotations.SerializedName

data class NewsDTO (
    @SerializedName("news_url") val news_url : String?,
    @SerializedName("image_url") val image_url : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("text") val text : String?,
    @SerializedName("source_name") val source_name : String?,
    @SerializedName("date") val date : String?,
    @SerializedName("topics") val topics : List<String>?,
    @SerializedName("sentiment") val sentiment : String?,
    @SerializedName("type") val type : String?,
    @SerializedName("tickers") val tickers : List<String>?
        )


fun NewsDTO.toNews(): News {
    return News(
        news_url = news_url?:"",
        image_url = image_url?:"",
        title = title?:"",
        text = text?:"",
        source_name = source_name?:"",
        date = date?:"",
        sentiment = sentiment?:"",
        type = type?:"",
        topics = topics?: listOf(),
        tickers = tickers?: listOf(),
    )
}