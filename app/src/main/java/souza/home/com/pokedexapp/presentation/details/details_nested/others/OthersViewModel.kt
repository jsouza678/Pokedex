package souza.home.com.pokedexapp.presentation.details.details_nested.others

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
import souza.home.com.pokedexapp.data.pokedex.PropertiesRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.NestedType
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.PropertyResponse
import souza.home.com.pokedexapp.di.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeProperty

class OthersViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private val _internetStatus = MutableLiveData<Boolean>()
    val internetStatus : LiveData<Boolean>
        get() = _internetStatus
    private var _statusAb = MutableLiveData<AbilityPokedexStatus>()
    val statusAb : LiveData<AbilityPokedexStatus>
        get() = _statusAb
    private var _abilityDesc = MutableLiveData<String>()
    val abilityDesc : LiveData<String>
        get() = _abilityDesc
    private var _pokeTypes = MutableLiveData<MutableList<NestedType>>()
    val pokeTypes : LiveData<MutableList<NestedType>>
        get() = _pokeTypes
    private var _other = MutableLiveData<PropertyResponse>()
    val other : LiveData<PropertyResponse>
        get() = _other
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val propertiesRepository =
        PropertiesRepositoryImpl(pokemon, app.applicationContext)
    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = propertiesRepository.properties

    init {
        _internetStatus.value = isConnected
        if(isConnected){
            _pokeTypes.value = mutableListOf()
            getOtherProperties(pokemon)
        }
    }

    private fun getOtherProperties(pokemon: Int) {
        coroutineScope.launch {
            propertiesRepository.refreshProperties(pokemon)
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

                _abilityDesc.value = abilityData.effect?.get(0)?.effect

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

enum class AbilityPokedexStatus{ LOADING, ERROR, DONE}

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}