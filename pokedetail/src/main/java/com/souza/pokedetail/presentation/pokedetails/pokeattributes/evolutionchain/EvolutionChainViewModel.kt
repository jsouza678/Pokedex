package com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.souza.pokedetail.domain.model.PokeEvolutionChain
import com.souza.pokedetail.domain.usecase.FetchEvolutionChainFromApi
import com.souza.pokedetail.domain.usecase.GetEvolutionChainFromDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EvolutionChainViewModel(
    private val pokemonId: Int,
    private val fetchEvolutionChainFromApi: FetchEvolutionChainFromApi,
    private val getEvolutionChainFromDatabase: GetEvolutionChainFromDatabase
) : ViewModel() {

    fun updateEvolutionOnViewLiveData(): LiveData<PokeEvolutionChain>? = getEvolutionChainFromDatabase(pokemonId)
    private val coroutineScope = Dispatchers.IO

    init {
        getChainEvolution(pokemonId)
    }

    private fun getChainEvolution(chainId: Int) {
        viewModelScope.launch(coroutineScope) {
            fetchEvolutionChainFromApi(chainId)
        }
    }
}
