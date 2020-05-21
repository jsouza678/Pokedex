package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.VarietyRepository

class FetchVarietiesFromApi(private val varietyRepository: VarietyRepository) {
    suspend operator fun invoke(id: Int) = varietyRepository.refreshVarieties(id)
}
