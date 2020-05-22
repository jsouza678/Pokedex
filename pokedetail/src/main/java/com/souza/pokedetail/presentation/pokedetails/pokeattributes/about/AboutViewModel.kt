package com.souza.pokedetail.presentation.pokedetails.pokeattributes.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.domain.usecase.FetchVarietiesFromApi
import com.souza.pokedetail.domain.usecase.GetVarietiesFromDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AboutViewModel(
    private val pokemonId: Int,
    private val fetchVarietiesFromApi: FetchVarietiesFromApi,
    private val getVarietiesFromDatabase: GetVarietiesFromDatabase
) : ViewModel() {

    private val coroutineScope = Dispatchers.IO
    fun updateVariationsOnViewLiveData(): LiveData<PokeVariety?>? = getVarietiesFromDatabase(pokemonId)

    init {
        getVarieties(pokemonId)
    }

    fun getVarieties(pokemon: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchVarietiesFromApi(pokemon)
        }
    }
}
