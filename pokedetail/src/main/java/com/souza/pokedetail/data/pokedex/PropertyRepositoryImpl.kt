package com.souza.pokedetail.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokedetail.data.pokedex.local.PropertyDao
import com.souza.pokedetail.data.pokedex.mapper.PokedexMapper
import com.souza.pokedetail.data.pokedex.remote.PokeDetailService
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.repository.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PropertyRepositoryImpl(
    private val pokeDetailService: PokeDetailService,
    private val propertyDao: PropertyDao
) : PropertyRepository {

    override fun getProperties(id: Int): LiveData<PokeProperty>? {
        return propertyDao.getProperty(id)?.let {
            Transformations.map(it) { propertyObject ->
                propertyObject?.let { propertyItem -> PokedexMapper.propertyEntityAsDomainModel(
                    propertyEntity = propertyItem
                ) }
            }
        }
    }

    override suspend fun refreshProperties(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeProperty = pokeDetailService.fetchPokeStatsAsync(id).await()
                propertyDao.insertAll(PokedexMapper.propertyResponseAsDatabaseModel(
                    propertyRootResponse = pokeProperty
                ))
            } catch (e: Exception) {}
        }
    }
}
