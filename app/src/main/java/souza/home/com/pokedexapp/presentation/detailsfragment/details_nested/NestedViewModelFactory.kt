package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.detailsfragment.DetailsViewModel
import souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.about.AboutViewModel
import souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.others.OthersViewModel
import souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.stats.StatsViewModel

class NestedViewModelFactory(private val pokemon: Int, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(pokemon, application) as T
        } else if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(pokemon, application) as T
        } else if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            return AboutViewModel(pokemon, application) as T
        } else if (modelClass.isAssignableFrom(OthersViewModel::class.java)) {
            return OthersViewModel(pokemon, application) as T
        }

        throw IllegalArgumentException(application.applicationContext.getString(R.string.unknown_viewmodel))
    }
}
