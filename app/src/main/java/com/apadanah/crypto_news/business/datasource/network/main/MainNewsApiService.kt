package com.apadanah.crypto_news.business.datasource.network.main

import com.apadanah.crypto_news.business.datasource.network.main.responses.NewsListDTO
import com.apadanah.crypto_news.business.domain.constans.Constants
import retrofit2.http.*

interface MainNewsApiService {


    // For now we are not gonna use this
    @GET("/category")
    suspend fun getAllNewsAPI(
        @Query("token") token: String,
        @Query("page") page: Int,
        @Query("items") items: Int = Constants.DEFAULT_ITEM_SIZE,
        @Query("section") section: String = Constants.ALL_TICKERS_PARAMETER,
    ): NewsListDTO


    @GET()
    suspend fun getAllNewsByFilterAPI(
        @Url url: String, // example: /category or empty
        @Query("token") token: String,
        @Query("page") page: Int,
        @QueryMap fields: MutableMap<String, String>, // section || tickers || topic || source || type || sortby
        @Query("items") items: Int = Constants.DEFAULT_ITEM_SIZE,
    ): NewsListDTO


}