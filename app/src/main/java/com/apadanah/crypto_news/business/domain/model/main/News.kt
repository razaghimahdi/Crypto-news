package com.apadanah.crypto_news.business.domain.model.main

data class News(
    val news_url: String,
    val image_url: String,
    val title: String,
    val text: String,
    val source_name: String,
    val date: String,
    val topics: List<String>,
    val sentiment: String,
    val type: String,
    val tickers: List<String>
)