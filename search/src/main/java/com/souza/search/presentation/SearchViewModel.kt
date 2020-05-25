package com.souza.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.search.domain.usecase.SearchPokesById
import com.souza.search.domain.usecase.SearchPokesByName

class SearchViewModel(
    var searchPokesByNameUseCase: SearchPokesByName,
    var searchPokesByIdUseCase: SearchPokesById
) : ViewModel() {

    fun searchPokesById(id: Int): LiveData<List<Pokemon>?> {
        return searchPokesByIdUseCase(id)
    }
    fun searchPokesByName(name: String): LiveData<List<Pokemon>?> {
        return searchPokesByNameUseCase(name)
    }
}
