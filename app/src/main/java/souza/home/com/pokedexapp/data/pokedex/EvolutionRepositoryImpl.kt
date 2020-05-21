package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.EvolutionChainDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.repository.EvolutionRepository

class EvolutionRepositoryImpl(
    private val evolutionChainDao: EvolutionChainDao,
    private val pokedexService: PokedexService
) : EvolutionRepository {

    override fun getEvolutionChain(id: Int): LiveData<PokeEvolutionChain>? {
        return evolutionChainDao.getEvolutionChain(id)?.let {
            Transformations.map(it) { evolutionObject ->
                evolutionObject?.let { evolutionItem -> PokedexMapper.evolutionEntityAsDomainModel(evolutionItem) }
            }
        }
    }

    override suspend fun refreshEvolutionChain(id: Int) {
        withContext(Dispatchers.IO) {
            val pokeEvolution = pokedexService.fetchEvolutionChainAsync(id).await()
            evolutionChainDao.insertAll(PokedexMapper.evolutionChainResponseToDatabaseModel(pokeEvolution))
        }
    }
}
