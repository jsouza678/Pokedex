package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val pokedexService: PokedexService,
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
            val pokeList = pokedexService.fetchPokesAsync(page).await()
            PokedexMapper.pokemonResponseAsDatabaseModel(pokeList)?.let { pokemonDao.insertAll(*it) }
        }
    }
}
