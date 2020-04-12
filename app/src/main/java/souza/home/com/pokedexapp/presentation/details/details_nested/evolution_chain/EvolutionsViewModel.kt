package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.EvolutionRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain

class EvolutionsViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    fun updateEvolutionOnViewLiveData(): LiveData<PokeEvolutionChain>? = chainRepository.evolution
    private var chainRepository = EvolutionRepositoryImpl(pokemon, app.applicationContext)
    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init{
        if(isConnected){
            getChainEvolution(pokemon)
        }
    }

    private fun getChainEvolution(chainId: Int){
        coroutineScope.launch {
            chainRepository.refreshEvolutionChain(chainId)
        }
    }
}