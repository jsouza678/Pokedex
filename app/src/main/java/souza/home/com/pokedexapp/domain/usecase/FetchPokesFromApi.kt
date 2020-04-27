package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class FetchPokesFromApi (private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(page: Int) = pokemonRepository.refreshPokes(page)
}
