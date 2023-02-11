package com.apadanah.crypto_news.business.datasource.network.main.responses

import com.google.gson.annotations.SerializedName


data class NewsListDTO (
	@SerializedName("data") val data : List<NewsDTO>
)