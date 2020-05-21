package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.VarietiesDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.repository.VarietyRepository

class VarietyRepositoryImpl(
    private val varietiesDao: VarietiesDao,
    private val pokedexService: PokedexService
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
                val pokeVariations = pokedexService.fetchVariationsAsync(id).await()
                varietiesDao.insertAll(PokedexMapper.variationsResponseAsDatabaseModel(pokeVariations))
            } catch (e: Exception) {}
        }
    }
}
