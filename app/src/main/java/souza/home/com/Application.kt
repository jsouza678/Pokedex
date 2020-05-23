package souza.home.com

import android.app.Application
import com.souza.home.di.homeModule
import com.souza.pokedetail.di.pokeDetailModule
import com.souza.search.di.searchModule
import com.souza.shared_components.di.sharedModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import org.koin.core.logger.Logger
import souza.home.com.pokecatalog.di.pokeCatalogModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            logger(koinLogger())
            modules(
                listOf(
                    homeModule,
                    pokeCatalogModule,
                    pokeDetailModule,
                    searchModule,
                    sharedModule
                )
            )
        }
    }

    private fun koinLogger(): Logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
}
