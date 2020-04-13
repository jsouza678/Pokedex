package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.di.PokeApi
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

enum class HomePokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokemonRepositoryImpl(context: Context) : PokemonRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    override val pokes: LiveData<List<Poke>?> = Transformations.map(DB_INSTANCE.pokemonDao.getPokes()){
        PokedexMapper.pokemonAsDomain(it)
    }

    private val _internet = MutableLiveData<HomePokedexStatus>()

    val internet : LiveData<HomePokedexStatus>
    get() = _internet

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO){
            _internet.postValue(HomePokedexStatus.LOADING)
            try{
                val pokeList = PokeApi.retrofitService.getPokes(page).await()
                PokedexMapper.pokemonToDatabaseModel(pokeList)?.let { DB_INSTANCE.pokemonDao.insertAll(*it) }
                if(pokeList.results.isNullOrEmpty()){
                    _internet.postValue(HomePokedexStatus.EMPTY)
                }else{
                    _internet.postValue(HomePokedexStatus.DONE)
                }
            }catch(e: Exception){
                _internet.postValue(HomePokedexStatus.ERROR)
                Log.i("Error" , "Message From Api on Pokemon" + e.message)
            }
        }
    }
}