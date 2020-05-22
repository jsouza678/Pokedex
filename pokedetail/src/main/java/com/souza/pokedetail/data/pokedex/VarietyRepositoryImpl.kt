package com.souza.pokedetail.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokedetail.data.pokedex.local.VarietiesDao
import com.souza.pokedetail.data.pokedex.mapper.PokedexMapper
import com.souza.pokedetail.data.pokedex.remote.PokeDetailService
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.domain.repository.VarietyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VarietyRepositoryImpl(
    private val varietiesDao: VarietiesDao,
    private val pokeDetailService: PokeDetailService
) : VarietyRepository {

    override fun getVarieties(id: Int): LiveData<PokeVariety?>? {
        val varieties = Transformations.map(varietiesDao.getVariety(id)) {
            it?.let { varietiesItem -> PokedexMapper.varietyEntityAsDomainModel(varietiesItem) }
        }
        return varieties
    }

    override suspend fun refreshVarieties(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeVariations = pokeDetailService.fetchVariationsAsync(id).await()
                varietiesDao.insertAll(PokedexMapper.variationsResponseAsDatabaseModel(pokeVariations))
            } catch (e: Exception) {}
        }
    }
}
