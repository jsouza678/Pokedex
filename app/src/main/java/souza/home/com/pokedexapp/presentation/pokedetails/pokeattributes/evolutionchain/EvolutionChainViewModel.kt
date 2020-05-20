package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.evolutionchain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.usecase.FetchEvolutionChainFromApi
import souza.home.com.pokedexapp.domain.usecase.GetEvolutionChainFromDatabase

class EvolutionChainViewModel(
    private val pokemonId: Int,
    private val fetchEvolutionChainFromApi: FetchEvolutionChainFromApi,
    private val getEvolutionChainFromDatabase: GetEvolutionChainFromDatabase
) : ViewModel() {

    fun updateEvolutionOnViewLiveData(): LiveData<PokeEvolutionChain>? = getEvolutionChainFromDatabase(pokemonId)
    private val coroutineScope = Dispatchers.IO

    init {
        getChainEvolution(pokemonId)
    }

    private fun getChainEvolution(chainId: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchEvolutionChainFromApi(chainId)
        }
    }
}
