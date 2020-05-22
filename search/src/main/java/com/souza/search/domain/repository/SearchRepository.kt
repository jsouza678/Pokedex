package com.souza.search.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokecatalog.domain.model.Pokemon

interface SearchRepository {

    fun searchPokesById(poke: Int): LiveData<List<Pokemon>?>

    fun searchPokesByName(poke: String): LiveData<List<Pokemon>?>
}
