package souza.home.com.pokedexapp.presentation.details.details_nested.others

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.PokemonProperty
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.PokemonNested

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

enum class AbilityPokedexStatus{ LOADING, ERROR, DONE}

class PokeOthersViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _statusAb = MutableLiveData<AbilityPokedexStatus>()

    val statusAb : LiveData<AbilityPokedexStatus>
        get() = _statusAb

    private var _abilityDesc = MutableLiveData<String>()

    val abilityDesc : LiveData<String>
        get() = _abilityDesc

    private var _pokeTypes = MutableLiveData<MutableList<PokemonNested>>()

    val pokeTypes : LiveData<MutableList<PokemonNested>>
        get() = _pokeTypes

    private var _other = MutableLiveData<PokemonProperty>()

    val other : LiveData<PokemonProperty>
        get() = _other

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _pokeTypes.value = mutableListOf()
        getOtherProperties(pokemon)
    }

    private fun getOtherProperties(pokemon: Int) {

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            val getStatsDeferred = PokeApi.retrofitService.getPokeStats(pokemon)
            try{
                val listResult = getStatsDeferred.await()

                _other.value = listResult
                _status.value = DetailsPokedexStatus.DONE

            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }
        }
    }


    fun getAbilityDesc(abilityId: Int){
        getAbilityData(abilityId)
    }



    fun getPokesInTypes(typeId: Int){
        getPokesFromTypes(typeId)
    }


    private fun getAbilityData(abilityId: Int){
        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            val getAbilityDeferred = PokeApi.retrofitService.getAbilityData(abilityId)

            try{
                val abilityData = getAbilityDeferred.await()

                _abilityDesc.value = abilityData.effect[0].effect

                _statusAb.value = AbilityPokedexStatus.DONE

            }catch(t: Throwable){
                _statusAb.value = AbilityPokedexStatus.ERROR
            }
        }
    }

    private fun getPokesFromTypes(typeId: Int){

        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            val getTypesDeferred = PokeApi.retrofitService.getTypeData(typeId)

            try{
                val typesData = getTypesDeferred.await()

                _pokeTypes.value = typesData.pokemon

                _statusAb.value = AbilityPokedexStatus.DONE
            }catch(t: Throwable){
                _statusAb.value = AbilityPokedexStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}