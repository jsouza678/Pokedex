package com.souza.pokedetail.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokedetail.data.pokedex.local.TypeDao
import com.souza.pokedetail.data.pokedex.mapper.PokedexMapper
import com.souza.pokedetail.data.pokedex.remote.PokeDetailService
import com.souza.pokedetail.domain.model.PokeType
import com.souza.pokedetail.domain.repository.TypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TypeRepositoryImpl(
    private val typeDao: TypeDao,
    private val pokeDetailService: PokeDetailService
) : TypeRepository {

    override fun getPokesInType(id: Int): LiveData<PokeType>? {
        return typeDao.getTypes(id).let {
            Transformations.map(it) { typeObject ->
                typeObject?.let { typeItem ->
                    PokedexMapper
                        .typeAsDomainModel(typeEntity = typeItem)
                }
            }
        }
    }

    override suspend fun refreshTypes(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeType = pokeDetailService.fetchTypeDataAsync(id).await()
                typeDao.insertAll(
                    PokedexMapper
                        .typeResponseAsDatabaseModel(typesRootResponse = pokeType)
                )
            } catch (e: Exception) {}
        }
    }
}
