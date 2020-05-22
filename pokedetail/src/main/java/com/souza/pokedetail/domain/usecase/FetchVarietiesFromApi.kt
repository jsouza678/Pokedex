package com.souza.pokedetail.domain.usecase

import com.souza.pokedetail.domain.repository.VarietyRepository

class FetchVarietiesFromApi(private val varietyRepository: VarietyRepository) {
    suspend operator fun invoke(id: Int) = varietyRepository.refreshVarieties(id)
}
