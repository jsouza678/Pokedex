package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.EvolutionRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.utils.CheckNetworkState

class EvolutionChainViewModel(pokemon: Int, app: Application) : AndroidViewModel(app) {

    fun updateEvolutionOnViewLiveData(): LiveData<PokeEvolutionChain>? = chainRepository.evolution
    private var chainRepository = EvolutionRepositoryImpl(pokemon, app.applicationContext)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        if (CheckNetworkState.checkNetworkState(app.applicationContext)) {
            getChainEvolution(pokemon)
        }
    }

    private fun getChainEvolution(chainId: Int) {
        coroutineScope.launch {
            chainRepository.refreshEvolutionChain(chainId)
        }
    }
}
