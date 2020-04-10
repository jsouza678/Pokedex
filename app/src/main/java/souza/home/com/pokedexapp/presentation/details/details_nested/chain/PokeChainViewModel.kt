package souza.home.com.pokedexapp.presentation.details.details_nested.chain

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
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
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
                val item = getChainDeferred.await()

                val evolutionArray : List<PokeEvolution>
                evolutionArray = ArrayList()
                evolutionArray.clear()

                val firstChain = item.chain.species?.name // this represents the first chain of evolution
                val secondChain = item.chain.evolves_to?.get(0)?.species?.name // this represents the second chain of evolution
                val thirdChain = item.chain.evolves_to?.get(0)?.evolves_to?.get(0)?.species?.name // this represents the third chain of evolution

                try{
                    firstChain.let { evolutionArray.add(item.chain) }
                }catch(e: Exception){
                    _status.value = DetailsPokedexStatus.EMPTY // if the poke doesn't have evolution chain
                }

                secondChain.let { item.chain.evolves_to?.get(0)?.let { it1 ->
                    evolutionArray.add(
                        it1
                    )
                } }

                thirdChain.let {
                    item.chain.evolves_to?.get(0)?.evolves_to?.get(0)?.let { it1 ->
                        evolutionArray.add(
                            it1
                        )
                    }
                }
                    _chain.value = evolutionArray

                    _status.value = DetailsPokedexStatus.DONE

        }
    }


    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}