package com.apadanah.crypto_news.di

import com.apadanah.crypto_news.business.datasource.network.main.MainNewsApiService
import com.apadanah.crypto_news.business.domain.constans.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val AppModule = module {

    single { createMainNewsApiService(get()) }

    single { createRetrofit(get()) }

    single { createOkHttpClient() }

    single { GsonConverterFactory.create() }


}


fun createMainNewsApiService(retrofit: Retrofit): MainNewsApiService {
    return retrofit.create(MainNewsApiService::class.java)
}
fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl( Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}
