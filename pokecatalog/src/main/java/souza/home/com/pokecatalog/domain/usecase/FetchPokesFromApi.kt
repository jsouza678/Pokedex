package souza.home.com.pokecatalog.domain.usecase

import souza.home.com.pokecatalog.domain.repository.PokemonRepository

class FetchPokesFromApi(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(page: Int) = pokemonRepository.refreshPokes(page)
}
