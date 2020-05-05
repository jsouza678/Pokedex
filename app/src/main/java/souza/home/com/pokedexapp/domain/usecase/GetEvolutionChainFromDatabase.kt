package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.repository.EvolutionRepository

class GetEvolutionChainFromDatabase(private val evolutionRepository: EvolutionRepository) {
    operator fun invoke(id: Int): LiveData<PokeEvolutionChain>? = evolutionRepository.getEvolutionChain(id)
}
