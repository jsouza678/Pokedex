package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain

interface EvolutionRepository {

    fun getEvolutionChain(id: Int): LiveData<PokeEvolutionChain>?

    suspend fun refreshEvolutionChain(id: Int)
}
