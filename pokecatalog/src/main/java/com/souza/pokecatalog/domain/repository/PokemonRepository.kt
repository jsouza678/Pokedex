package com.souza.pokecatalog.domain.repository

import androidx.lifecycle.LiveData
import com.souza.pokecatalog.domain.model.Pokemon

interface PokemonRepository {

    fun getPokes(): LiveData<List<Pokemon>?>

    suspend fun refreshPokes(page: Int)
}
