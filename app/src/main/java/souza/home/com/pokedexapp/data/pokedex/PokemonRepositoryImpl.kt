package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val context: Context) : PokemonRepository {

    private val INSTANCE = PokemonDatabase.getDatabase(context)

    override val pokes: LiveData<List<Poke>?> = Transformations.map(INSTANCE.pokemonDao.getPokes()) {
        PokedexMapper.pokemonAsDomain(it)
    }

    private val _internet = MutableLiveData<HomePokedexStatus>()

    val internet: LiveData<HomePokedexStatus>
        get() = _internet

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO) {
            _internet.postValue(HomePokedexStatus.LOADING)
            try {
                val pokeList = PokeApi.retrofitService.getPokes(page).await()
                PokedexMapper.pokemonToDatabaseModel(pokeList)
                    ?.let { INSTANCE.pokemonDao.insertAll(*it) }
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
