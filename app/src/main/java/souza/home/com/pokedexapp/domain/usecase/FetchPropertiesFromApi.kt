package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.PropertiesRepository

class FetchPropertiesFromApi(private val propertiesRepository: PropertiesRepository) {
    suspend operator fun invoke(id: Int) = propertiesRepository.refreshProperties(id)
}
