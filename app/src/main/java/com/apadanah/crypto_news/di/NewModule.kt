package com.apadanah.crypto_news.di

import com.apadanah.crypto_news.business.datasource.network.main.MainNewsApiService
import com.apadanah.crypto_news.business.interactors.main.GetNews
import com.apadanah.crypto_news.presentation.fragments.main.MainNewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val NewsModule = module {

    viewModel { MainNewsViewModel(get()) }

    single { createGetNews(get()) }

}

fun createGetNews(
    service: MainNewsApiService,
): GetNews {
    return GetNews(
        service,
    )
}

