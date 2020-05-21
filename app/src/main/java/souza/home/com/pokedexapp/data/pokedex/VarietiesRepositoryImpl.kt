package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.VarietiesDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.repository.VarietiesRepository

class VarietiesRepositoryImpl(
    private val varietiesDao: VarietiesDao,
    private val pokedexService: PokedexService
) : VarietiesRepository {

    override fun getVarieties(id: Int): LiveData<PokeVariety?>? {
        val varieties = Transformations.map(varietiesDao.getVariety(id)) {
            it?.let { varietiesItem -> PokedexMapper.variationsEntityAsDomainModel(varietiesItem) }
        }
        return varieties
    }

    override suspend fun refreshVarieties(id: Int) {
        withContext(Dispatchers.IO) {
            val pokeVariations = pokedexService.fetchVariationsAsync(id).await()
            varietiesDao.insertAll(PokedexMapper.variationsResponseAsDatabaseModel(pokeVariations))
        }
    }
}
