package com.souza.pokedetail.domain.repository

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeEvolutionChain

interface EvolutionRepository {

    fun getEvolutionChain(id: Int): LiveData<PokeEvolutionChain>?

    suspend fun refreshEvolutionChain(id: Int)
}
