package souza.home.com.pokecatalog.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokecatalog.domain.model.Pokemon
import souza.home.com.pokecatalog.domain.repository.PokemonRepository

class GetPokesFromDatabase(private val pokemonRepository: PokemonRepository) {
    operator fun invoke(): LiveData<List<Pokemon>?> = pokemonRepository.getPokes()
}
