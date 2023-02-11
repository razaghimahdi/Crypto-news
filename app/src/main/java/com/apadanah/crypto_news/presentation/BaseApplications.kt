package com.apadanah.crypto_news.presentation

import android.app.Application
import androidx.multidex.MultiDex
import com.apadanah.crypto_news.di.AppModule
import com.apadanah.crypto_news.di.NewsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplications : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplications)
            modules(listOf(AppModule, NewsModule))
        }

    }
}