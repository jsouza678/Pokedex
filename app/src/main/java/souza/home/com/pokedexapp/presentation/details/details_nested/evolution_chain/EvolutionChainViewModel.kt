package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.VarietiesRepositoryImpl
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.PokeEvolution

class PokeChainViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()
    val status : LiveData<DetailsPokedexStatus>
        get() = _status
    private var _chain = MutableLiveData<MutableList<PokeEvolution>>()
    val chain : LiveData<MutableList<PokeEvolution>>
        get() = _chain
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety>? = varietiesRepository.varieties
    private val varietiesRepository = VarietiesRepositoryImpl(pokemon, app.applicationContext)
    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    init {
        if(isConnected){
            getPokeChainUrl(pokemon)
        }
    }

    private fun getPokeChainUrl(pokemon: String){
        coroutineScope.launch {
            varietiesRepository.refreshVarieties(pokemon)
        }
    }

    fun loadEvolutionChain(chainId: String){
        getChainEvolution(chainId)
    }

    private fun getChainEvolution(chainID: String){

        val evolutionChainID = chainID.substringAfterLast("n/").substringBeforeLast("/")
        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            val getChainDeferred = PokeApi.retrofitService.getEvolutionChain(evolutionChainID)
            try{
                val item = getChainDeferred.await()
                val evolutionArray : List<PokeEvolution>

                evolutionArray = ArrayList()
                evolutionArray.clear()

                if(item.chain.species?.name != null){ // 1 If poke has one evolution
                    evolutionArray.add(item.chain)
                    try{
                        evolutionArray.add(item.chain.evolves_to!![0]) // 2 If poke has the second evolution
                        try {
                            evolutionArray.add(item.chain.evolves_to!![0].evolves_to!![0]) // 3 If poke has the third evolution
                        }catch (e: Exception){ }
                    }
                    catch (e: Exception) { }
                }

                try {
                    _chain.value = evolutionArray
                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    _status.value = DetailsPokedexStatus.EMPTY
                }

            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}