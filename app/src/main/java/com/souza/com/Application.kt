package com.souza.com

import android.app.Application
import com.souza.home.di.homeModule
import com.souza.pokecatalog.di.pokeCatalogModule
import com.souza.pokedetail.di.pokeDetailModule
import com.souza.search.di.searchModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Logger

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            logger(koinLogger())
            modules(
                modules = listOf(
                    homeModule,
                    pokeCatalogModule,
                    pokeDetailModule,
                    searchModule
                )
            )
        }
    }

    private fun koinLogger(): Logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
}
