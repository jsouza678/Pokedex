package com.souza.pokedetail.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.souza.pokedetail.data.pokedex.local.AbilityDao
import com.souza.pokedetail.data.pokedex.mapper.PokedexMapper
import com.souza.pokedetail.data.pokedex.remote.PokeDetailService
import com.souza.pokedetail.domain.repository.AbilityRepository

class AbilityRepositoryImpl(
    private val pokeDetailService: PokeDetailService,
    private val abilityDao: AbilityDao
) : AbilityRepository {

    override fun getAbilityDescription(id: Int): LiveData<String>? {
        return Transformations.map(abilityDao.getAbility(id)) { abilityObject ->
            abilityObject?.let { abilityItem -> PokedexMapper.abilityAsDomainModel(abilityEntity = abilityItem).description }
        }
    }

    override suspend fun refreshAbilities(id: Int) {
        try {
            val pokeAbility = pokeDetailService.fetchAbilityDataAsync(id).await()
            abilityDao.insertAll(PokedexMapper.abilityResponseAsDatabaseModel(abilityRootResponse = pokeAbility))
        } catch (e: Exception) { }
    }
}
