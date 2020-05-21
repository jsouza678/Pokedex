package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.TypeRepository

class FetchPokesInTypesFromApi(private val typeRepository: TypeRepository) {
    suspend operator fun invoke(id: Int) = typeRepository.refreshTypes(id)
}
