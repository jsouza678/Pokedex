package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.di.PokeApi
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.repository.PokemonRepository

class PokemonRepositoryImpl(context: Context) : PokemonRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    override val pokes: LiveData<List<Poke>?> = Transformations.map(DB_INSTANCE.pokemonDao.getPokes()){
        PokedexMapper.pokemonAsDomain(it)
    }

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO){
            try{
                val pokeList = PokeApi.retrofitService.getPokes(page).await()
                PokedexMapper.pokemonToDatabaseModel(pokeList)?.let { DB_INSTANCE.pokemonDao.insertAll(*it) }
            }catch(e: Exception){
                Log.i("Error" , "Message From Api on Pokemon" + e.message)
            }
        }
    }
}