package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.TypeDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.PokeType
import souza.home.com.pokedexapp.domain.repository.TypeRepository

class TypeRepositoryImpl(
    private val typeDao: TypeDao,
    private val pokedexService: PokedexService
) : TypeRepository {

    override fun getPokesInType(id: Int): LiveData<PokeType>? {
        return typeDao.getTypes(id).let {
            Transformations.map(it) {
                it?.let { it1 -> PokedexMapper.typeAsDomainModel(it1) }
            }
        }
    }

    override suspend fun refreshTypes(id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeType = pokedexService.fetchTypeDataAsync(id).await()
                typeDao.insertAll(PokedexMapper.typeResponseAsDatabaseModel(pokeType))
            } catch (e: Exception) {}
        }
    }
}
