package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain

interface EvolutionRepository {

    val evolution : LiveData<PokeEvolutionChain>?

    suspend fun refreshEvolutionChain(id: Int)
}