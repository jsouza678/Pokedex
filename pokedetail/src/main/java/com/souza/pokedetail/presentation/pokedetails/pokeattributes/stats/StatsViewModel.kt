package com.souza.pokedetail.presentation.pokedetails.pokeattributes.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souza.pokedetail.data.pokedex.remote.response.PropertyRootResponse
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.usecase.FetchPropertiesFromApi
import com.souza.pokedetail.domain.usecase.GetPropertiesFromDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatsViewModel(
    private val pokemonId: Int,
    private val fetchPropertiesFromApi: FetchPropertiesFromApi,
    private val getPropertiesFromDatabase: GetPropertiesFromDatabase
) : ViewModel() {

    private var _stats = MutableLiveData<PropertyRootResponse>()

    val stats: LiveData<PropertyRootResponse>
        get() = _stats

    private val coroutineScope = Dispatchers.IO

    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = getPropertiesFromDatabase(pokemonId)

    init {
        getStats(pokemonId)
    }

    private fun getStats(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchPropertiesFromApi(pokemon)
        }
    }
}
