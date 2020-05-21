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
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO

class OthersViewModel(
    private val pokemonId: Int,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private var _abilityDesc = MutableLiveData<String>()
    val abilityDesc: LiveData<String>
        get() = _abilityDesc
    private var _pokeTypes = MutableLiveData<MutableList<TypeResponse>>()
    val pokeTypes: LiveData<MutableList<TypeResponse>>
        get() = _pokeTypes

    private val coroutineScope = Dispatchers.IO

    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemonId)

    init {
        getProperties(pokemonId)
    }

    private fun getProperties(pokemon: Int) {
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
        viewModelScope.launch(coroutineScope) {
            val getAbilityDeferred = PokeApi.retrofitService.fetchAbilityDataAsync(abilityId)
            val abilityData = getAbilityDeferred.await()
            _abilityDesc.postValue(abilityData.effect?.get(ABSOLUTE_ZERO)?.effect)
        }
    }

    private fun getPokesFromTypes(typeId: Int) {
        viewModelScope.launch(coroutineScope) {
            val getTypesDeferred = PokeApi.retrofitService.fetchTypeDataAsync(typeId)
            val typesData = getTypesDeferred.await().pokemon
            _pokeTypes.postValue(typesData)
        }
    }
}
