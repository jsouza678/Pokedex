package com.souza.pokedetail.domain.usecase

import com.souza.pokedetail.domain.repository.AbilityRepository

class FetchAbilityFromApi(private val abilityRepository: AbilityRepository) {
    suspend operator fun invoke(id: Int) = abilityRepository.refreshAbilities(id)
}
