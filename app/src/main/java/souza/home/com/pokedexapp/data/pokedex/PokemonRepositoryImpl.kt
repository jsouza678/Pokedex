package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val context: Context,
    private val pokedexService: PokedexService,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override fun getAllPokes(): LiveData<List<Poke>?> {
        val pokes =  Transformations.map(pokemonDao.getPokes()) {
            PokedexMapper.pokemonAsDomain(it)
        }
        return pokes
    }

    private val _internet = MutableLiveData<HomePokedexStatus>()

    val internet: LiveData<HomePokedexStatus>
        get() = _internet

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO) {
            _internet.postValue(HomePokedexStatus.LOADING)
            try {
                val pokeList = pokedexService.getPokes(page).await()
                PokedexMapper.pokemonToDatabaseModel(pokeList)
                    ?.let { pokemonDao.insertAll(*it) }
                _internet.postValue(HomePokedexStatus.DONE)
            } catch (e: Exception) {
                _internet.postValue(HomePokedexStatus.ERROR)
                Log.i(
                    context.getString(R.string.error_message_log),
                    context.getString(R.string.error_log_pokemon) + e.message
                )
            }
        }
    }
}

enum class HomePokedexStatus { LOADING, ERROR, DONE}
