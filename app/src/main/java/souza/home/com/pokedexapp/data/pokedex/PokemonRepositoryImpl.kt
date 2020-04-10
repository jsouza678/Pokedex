package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.domain.model.asDomainModel
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.remote.model.asDatabaseModel
import souza.home.com.pokedexapp.data.pokedex.local.PokemonsDatabase
import souza.home.com.pokedexapp.data.pokedex.remote.model.Poke

class PokemonRepositoryImpl(context: Context) : PokemonRepository{

    val DB_INSTANCE = PokemonsDatabase.getDatabase(context)

    override val pokes: LiveData<List<Poke>> = Transformations.map(DB_INSTANCE.pokemonDao.getPokes()){
        it.asDomainModel()
    }

    override suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO){
                val pokeList = PokeApi.retrofitService.getPokes(page).await()
                DB_INSTANCE.pokemonDao.insertAll(*pokeList.asDatabaseModel()!!)
        }
    }
}