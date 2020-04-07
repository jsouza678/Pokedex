package souza.home.com.pokedexapp.presenter.details.details_nested.chain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeChainViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _chain = MutableLiveData<MutableList<PokeEvolution>>()

    val chain : LiveData<MutableList<PokeEvolution>>
        get() = _chain

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPokeChainUrl(pokemon)
    }

    private fun getPokeChainUrl(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            val getPokeChainUrlDeferred = PokeApi.retrofitService.getVariations(pokemon)

            try{
                val result = getPokeChainUrlDeferred.await()

                val pokeId = result.evolution_chain.url?.substringAfterLast("n/")?.substringBeforeLast("/")
                try {
                    getChainEvolution(pokeId!!)
                } catch (e: Exception) {
                    // varietiesArray.add("No varieties")
                    _status.value = DetailsPokedexStatus.EMPTY
                }
            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }
        }
    }

    private fun getChainEvolution(pokemon: String){
        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            val getChainDeferred = PokeApi.retrofitService.getEvolutionChain(pokemon)
            try {
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

                        }catch (e: Exception){

                        }
                    }
                    catch (e: Exception) {

                    }
                }

                try {
                    _chain.value = evolutionArray

                    _status.value = DetailsPokedexStatus.DONE
                } catch (e: Exception) {
                    _status.value = DetailsPokedexStatus.EMPTY
                }

            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }}

    }
}