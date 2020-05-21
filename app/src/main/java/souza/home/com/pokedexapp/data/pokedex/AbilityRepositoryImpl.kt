package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import souza.home.com.pokedexapp.data.pokedex.local.AbilityDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.repository.AbilityRepository

class AbilityRepositoryImpl(
    private val pokedexService: PokedexService,
    private val abilityDao: AbilityDao
) : AbilityRepository {

    override fun getAbilityDescription(id: Int): LiveData<String>? {
        return Transformations.map(abilityDao.getAbility(id)) {
            it?.let { abilityItem -> PokedexMapper.abilityAsDomainModel(abilityItem).description }
        }
    }

    override suspend fun refreshAbilities(id: Int) {
        try {
            val pokeAbility = pokedexService.fetchAbilityDataAsync(id).await()
            abilityDao.insertAll(PokedexMapper.abilityResponseAsDatabaseModel(pokeAbility))
        } catch (e: Exception) { }
    }
}
