package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.others

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.response.TypeResponse
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.usecase.FetchPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase

class OthersViewModel(
    private val pokemonId: Int,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private val _internetStatus = MutableLiveData<Boolean>()
    val internetStatus: LiveData<Boolean>
        get() = _internetStatus
    private var _statusAb = MutableLiveData<AbilityPokedexStatus>()
    val statusAb: LiveData<AbilityPokedexStatus>
        get() = _statusAb
    private var _abilityDesc = MutableLiveData<String>()
    val abilityDesc: LiveData<String>
        get() = _abilityDesc
    private var _pokeTypes = MutableLiveData<MutableList<TypeResponse>>()
    val pokeTypes: LiveData<MutableList<TypeResponse>>
        get() = _pokeTypes

    private val coroutineScope = Dispatchers.IO

    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemonId)

    init {
        _pokeTypes.value = mutableListOf()
        getOtherProperties(pokemonId)
    }

    private fun getOtherProperties(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchPropertiesFromApi(pokemon)
        }
    }

    fun getAbilityDesc(abilityId: Int) {
        getAbilityData(abilityId)
    }

    fun getPokesInTypes(typeId: Int) {
        getPokesFromTypes(typeId)
    }

    private fun getAbilityData(abilityId: Int) {
        _statusAb.value = AbilityPokedexStatus.LOADING

        viewModelScope.launch(coroutineScope) {
            val getAbilityDeferred = PokeApi.retrofitService.fetchAbilityDataAsync(abilityId)

            try {
                val abilityData = getAbilityDeferred.await()

                _abilityDesc.postValue(abilityData.effect?.get(0)?.effect)

                _statusAb.postValue(AbilityPokedexStatus.DONE)
            } catch (t: Throwable) {
                _statusAb.postValue(AbilityPokedexStatus.ERROR)
            }
        }
    }

    private fun getPokesFromTypes(typeId: Int) {

        _statusAb.value = AbilityPokedexStatus.LOADING

        viewModelScope.launch(coroutineScope) {
            val getTypesDeferred = PokeApi.retrofitService.fetchTypeDataAsync(typeId)

            try {
                val typesData = getTypesDeferred.await()
                _pokeTypes.postValue(typesData.pokemon)
                _statusAb.postValue(AbilityPokedexStatus.DONE)
            } catch (t: Throwable) {
                _statusAb.postValue(AbilityPokedexStatus.ERROR)
            }
        }
    }
}

enum class AbilityPokedexStatus { LOADING, ERROR, DONE }
