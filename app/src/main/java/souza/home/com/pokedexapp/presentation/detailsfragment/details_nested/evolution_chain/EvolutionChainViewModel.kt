package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.evolution_chain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.usecase.GetEvolutionChainFromApi
import souza.home.com.pokedexapp.domain.usecase.GetEvolutionChainFromDatabase

class EvolutionChainViewModel(var pokemon: Int,
                              var getEvolutionChainFromApi: GetEvolutionChainFromApi,
                              var getEvolutionChainFromDatabase: GetEvolutionChainFromDatabase
) : ViewModel() {

    fun updateEvolutionOnViewLiveData(): LiveData<PokeEvolutionChain>? = getEvolutionChainFromDatabase(pokemon)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getChainEvolution(pokemon)
    }

    private fun getChainEvolution(chainId: Int) {
        coroutineScope.launch {
            getEvolutionChainFromApi(chainId)
        }
    }
}
