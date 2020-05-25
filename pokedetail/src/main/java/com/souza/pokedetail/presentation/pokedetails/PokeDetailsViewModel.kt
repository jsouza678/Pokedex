package com.souza.pokedetail.presentation.pokedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.domain.usecase.FetchPropertiesFromApi
import com.souza.pokedetail.domain.usecase.FetchVarietiesFromApi
import com.souza.pokedetail.domain.usecase.GetPropertiesFromDatabase
import com.souza.pokedetail.domain.usecase.GetVarietiesFromDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeDetailsViewModel(
    private val pokemonId: Int,
    private val fetchVarietiesFromApi: FetchVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemonId)
    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemonId)

    init {
        getColor(pokemonId)
    }

    private fun getColor(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchVarietiesFromApi(pokemon)
            fetchPropertiesFromApi(pokemon)
        }
    }
}
