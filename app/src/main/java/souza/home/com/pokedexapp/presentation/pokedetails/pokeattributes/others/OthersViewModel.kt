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
import souza.home.com.pokedexapp.domain.usecase.FetchAbilityFromApi
import souza.home.com.pokedexapp.domain.usecase.FetchPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetAbilityFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase

class OthersViewModel(
    private val pokemonId: Int,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase,
    private val fetchAbilityFromApi: FetchAbilityFromApi,
    private val getAbilityFromDatabase: GetAbilityFromDatabase
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

    fun getAbilityDesc(abilityId: Int): LiveData<String>? {
        getAbilityData(abilityId)

        return getAbilityFromDatabase(abilityId)
    }

    fun getPokesInTypes(typeId: Int) {
        getPokesFromTypes(typeId)
    }

    private fun getAbilityData(abilityId: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchAbilityFromApi(abilityId)
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
