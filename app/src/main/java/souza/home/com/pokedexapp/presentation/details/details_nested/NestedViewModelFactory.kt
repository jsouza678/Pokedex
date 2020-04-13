package souza.home.com.pokedexapp.presentation.details.details_nested

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.details.DetailsViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.about.AboutViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain.EvolutionChainViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.others.OthersViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.stats.PokeStatsViewModel

class NestedViewModelFactory(private val pokemon: Int, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(PokeStatsViewModel::class.java)){
            return PokeStatsViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(AboutViewModel::class.java)){
            return AboutViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(EvolutionChainViewModel::class.java)){
            return EvolutionChainViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(OthersViewModel::class.java)){
            return OthersViewModel(pokemon, application) as T

        } else if(modelClass.isAssignableFrom(EvolutionChainViewModel::class.java)){
            return EvolutionChainViewModel(pokemon, application) as T
        }

        throw IllegalArgumentException(application.applicationContext.getString(R.string.unknown_viewmodel))
    }
}