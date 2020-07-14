package com.souza.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.search.domain.usecase.SearchPokesByName

class PokeSearchViewModel(
    var searchPokesByNameUseCase: SearchPokesByName
) : ViewModel() {

    fun searchPokesByName(name: String): LiveData<List<Pokemon>?> {
        return searchPokesByNameUseCase(name)
    }
}
