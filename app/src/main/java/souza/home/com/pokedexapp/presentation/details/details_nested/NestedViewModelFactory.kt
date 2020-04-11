package souza.home.com.pokedexapp.presentation.details.details_nested

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import souza.home.com.pokedexapp.presentation.details.DetailsPokedexViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.about.PokeAboutViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain.PokeChainViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.others.PokeOthersViewModel
import souza.home.com.pokedexapp.presentation.details.details_nested.stats.PokeStatsViewModel

class NestedViewModelFactory(private val pokemon: Int, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsPokedexViewModel::class.java)) {
            return DetailsPokedexViewModel(
                pokemon,
                application
            ) as T
        }else if(modelClass.isAssignableFrom(PokeStatsViewModel::class.java)){
            return PokeStatsViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(PokeAboutViewModel::class.java)){
            return PokeAboutViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(PokeChainViewModel::class.java)){
            return PokeChainViewModel(pokemon, application) as T

        }else if(modelClass.isAssignableFrom(PokeOthersViewModel::class.java)){
            return PokeOthersViewModel(pokemon, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}