package com.souza.pokedetail.domain.usecase

import com.souza.pokedetail.domain.repository.EvolutionRepository

class FetchEvolutionChainFromApi(private val evolutionRepository: EvolutionRepository) {
    suspend operator fun invoke(id: Int) = evolutionRepository.refreshEvolutionChain(id)
}
