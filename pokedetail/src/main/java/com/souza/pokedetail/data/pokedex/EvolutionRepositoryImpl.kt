package com.souza.pokedetail.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokedetail.data.pokedex.local.EvolutionChainDao
import com.souza.pokedetail.data.pokedex.mapper.PokedexMapper
import com.souza.pokedetail.data.pokedex.remote.PokeDetailService
import com.souza.pokedetail.domain.model.PokeEvolutionChain
import com.souza.pokedetail.domain.repository.EvolutionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EvolutionRepositoryImpl(
    private val evolutionChainDao: EvolutionChainDao,
    private val pokeDetailService: PokeDetailService
) : EvolutionRepository {

    override fun getEvolutionChain(id: Int): LiveData<PokeEvolutionChain>? {
        return evolutionChainDao.getEvolutionChain(id)?.let {
            Transformations.map(it) { evolutionObject ->
                evolutionObject?.let { evolutionItem -> PokedexMapper.evolutionEntityAsDomainModel(
                    evolutionEntity = evolutionItem
                ) }
            }
        }
    }

    override suspend fun refreshEvolutionChain(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeEvolution = pokeDetailService.fetchEvolutionChainAsync(id).await()
                evolutionChainDao.insertAll(PokedexMapper.evolutionChainResponseToDatabaseModel(
                    evolutionChainResponse = pokeEvolution
                ))
            } catch (e: Exception) {}
        }
    }
}
