package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class GetPokesFromDatabase (private val pokemonRepository: PokemonRepository) {
    operator fun invoke(): LiveData<List<Poke>?> = pokemonRepository.getAllPokes()
}
