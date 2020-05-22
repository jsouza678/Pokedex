package souza.home.com.pokecatalog.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokecatalog.data.pokedex.local.PokemonDao
import souza.home.com.pokecatalog.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokecatalog.data.pokedex.remote.PokeCatalogService
import souza.home.com.pokecatalog.domain.model.Pokemon
import souza.home.com.pokecatalog.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val pokeCatalogService: PokeCatalogService,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override fun getPokes(): LiveData<List<Pokemon>?> {
        val pokes = Transformations.map(pokemonDao.getPokes()) { it ->
            PokedexMapper.pokemonEntityAsDomainModel(it)
        }
        return pokes
    }

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO) {
            try {
                val pokeList = pokeCatalogService.fetchPokesAsync(page).await()
                PokedexMapper.pokemonResponseAsDatabaseModel(pokeList)?.let { pokemonDao.insertAll(*it) }
            } catch (e: Exception) { }
        }
    }
}
