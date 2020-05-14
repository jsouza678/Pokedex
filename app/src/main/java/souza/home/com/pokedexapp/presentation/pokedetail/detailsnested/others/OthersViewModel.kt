package souza.home.com.pokedexapp.presentation.pokedetail.detailsnested.others

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.response.NestedTypeResponse
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase

class OthersViewModel(
    private val pokemon: Int,
    private val getPropertiesFromApi: GetPropertiesFromApi,
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
    private var _pokeTypes = MutableLiveData<MutableList<NestedTypeResponse>>()
    val pokeTypes: LiveData<MutableList<NestedTypeResponse>>
        get() = _pokeTypes

    private val coroutineScope = Dispatchers.IO

    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemon)

    init {
        _pokeTypes.value = mutableListOf()
        getOtherProperties(pokemon)
    }

    private fun getOtherProperties(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            getPropertiesFromApi(pokemon)
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
            val getAbilityDeferred = PokeApi.retrofitService.getAbilityData(abilityId)

            try {
                val abilityData = getAbilityDeferred.await()

                _abilityDesc.value = abilityData.effect?.get(0)?.effect

                _statusAb.value = AbilityPokedexStatus.DONE
            } catch (t: Throwable) {
                _statusAb.value = AbilityPokedexStatus.ERROR
            }
        }
    }

    private fun getPokesFromTypes(typeId: Int) {

        _statusAb.value = AbilityPokedexStatus.LOADING

        viewModelScope.launch(coroutineScope) {
            val getTypesDeferred = PokeApi.retrofitService.getTypeData(typeId)

            try {
                val typesData = getTypesDeferred.await()

                _pokeTypes.value = typesData.pokemon

                _statusAb.value = AbilityPokedexStatus.DONE
            } catch (t: Throwable) {
                _statusAb.value = AbilityPokedexStatus.ERROR
            }
        }
    }
}

enum class AbilityPokedexStatus { LOADING, ERROR, DONE }
