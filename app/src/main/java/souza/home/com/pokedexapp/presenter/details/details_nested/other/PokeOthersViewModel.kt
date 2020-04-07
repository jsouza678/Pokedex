package souza.home.com.pokedexapp.presenter.details.details_nested.other

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import souza.home.com.pokedexapp.network.model.types.PokemonNested

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

enum class AbilityPokedexStatus{ LOADING, ERROR, DONE}

class PokeOthersViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

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

    private fun getOtherProperties(pokemon: String) {

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


    fun getAbilityDesc(abId: String){
        getAbilityData(abId)
    }



    fun getPokesInTypes(typeId: String){
        getPokesFromTypes(typeId)
    }


    private fun getAbilityData(abId: String){
        _statusAb.value = AbilityPokedexStatus.LOADING

        coroutineScope.launch {
            val getAbilityDeferred = PokeApi.retrofitService.getAbilityData(abId)

            try{
                val abilityData = getAbilityDeferred.await()

                _abilityDesc.value = abilityData.effect[0].effect

                _statusAb.value = AbilityPokedexStatus.DONE

            }catch(t: Throwable){
                _statusAb.value = AbilityPokedexStatus.ERROR
            }
        }
    }

    private fun getPokesFromTypes(typeId: String){

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
}