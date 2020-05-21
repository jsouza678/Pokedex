package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.PropertyRepository

class FetchPropertiesFromApi(private val propertyRepository: PropertyRepository) {
    suspend operator fun invoke(id: Int) = propertyRepository.refreshProperties(id)
}
