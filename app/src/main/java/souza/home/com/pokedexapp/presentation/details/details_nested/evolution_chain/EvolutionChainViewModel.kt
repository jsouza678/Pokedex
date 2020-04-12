package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide.init
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.EvolutionRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.Evolution
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.utils.cropPokeUrl

class EvolutionChainViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety>? = varietiesRepository.varieties
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val varietiesRepository = VarietiesRepositoryImpl(pokemon, app.applicationContext)
    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    init {
        if(isConnected){
            getPokeChainUrl(pokemon)
        }
    }

    private fun getPokeChainUrl(pokemon: Int){
        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
        }
    }
}

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}