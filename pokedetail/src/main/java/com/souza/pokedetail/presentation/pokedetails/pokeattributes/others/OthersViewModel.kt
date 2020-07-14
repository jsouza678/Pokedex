package com.souza.pokedetail.presentation.pokedetails.pokeattributes.others

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.model.PokeType
import com.souza.pokedetail.domain.usecase.FetchAbilityFromApi
import com.souza.pokedetail.domain.usecase.FetchPokesInTypesFromApi
import com.souza.pokedetail.domain.usecase.FetchPropertiesFromApi
import com.souza.pokedetail.domain.usecase.GetAbilityFromDatabase
import com.souza.pokedetail.domain.usecase.GetPokesInTypesFromDatabase
import com.souza.pokedetail.domain.usecase.GetPropertiesFromDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OthersViewModel(
    private val pokemonId: Int,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase,
    private val fetchAbilityFromApi: FetchAbilityFromApi,
    private val getAbilityFromDatabase: GetAbilityFromDatabase,
    private val fetchPokesInTypesFromApi: FetchPokesInTypesFromApi,
    private val getPokesInTypesFromDatabase: GetPokesInTypesFromDatabase
) : ViewModel() {

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

    fun getPokesInTypes(typeId: Int): LiveData<PokeType>? {
        getPokesFromTypes(typeId)

        return getPokesInTypesFromDatabase(typeId)
    }

    private fun getAbilityData(abilityId: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchAbilityFromApi(abilityId)
        }
    }

    private fun getPokesFromTypes(typeId: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchPokesInTypesFromApi(typeId)
        }
    }
}
