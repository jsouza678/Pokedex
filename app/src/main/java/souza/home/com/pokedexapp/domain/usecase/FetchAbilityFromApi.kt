package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.AbilityRepository

class FetchAbilityFromApi(private val abilityRepository: AbilityRepository) {
    suspend operator fun invoke(id: Int) = abilityRepository.refreshAbilities(id)
}
