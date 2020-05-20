package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.VarietiesRepository

class FetchVarietiesFromApi(private val varietiesRepository: VarietiesRepository) {
    suspend operator fun invoke(id: Int) = varietiesRepository.refreshVarieties(id)
}
