package souza.home.com.pokedexapp.domain.usecase

import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class GetPokesFromApi(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(page: Int) = pokemonRepository.refreshPokes(page)
}
