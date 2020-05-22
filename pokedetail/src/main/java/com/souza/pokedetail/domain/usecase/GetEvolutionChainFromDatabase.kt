package com.souza.pokedetail.domain.usecase

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeEvolutionChain
import com.souza.pokedetail.domain.repository.EvolutionRepository

class GetEvolutionChainFromDatabase(private val evolutionRepository: EvolutionRepository) {
    operator fun invoke(id: Int): LiveData<PokeEvolutionChain>? = evolutionRepository.getEvolutionChain(id)
}
