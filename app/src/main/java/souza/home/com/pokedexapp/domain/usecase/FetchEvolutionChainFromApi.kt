package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.repository.EvolutionRepository

class FetchEvolutionChainFromApi (private val evolutionRepository: EvolutionRepository) {
    suspend operator fun invoke(id: Int) = evolutionRepository.refreshEvolutionChain(id)
}
