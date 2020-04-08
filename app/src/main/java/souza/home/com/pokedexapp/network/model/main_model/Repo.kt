package souza.home.com.pokedexapp.network.model.main_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.network.PokeApi

class Repo(private val database: PokemonsDatabase) {

    val pokes: LiveData<List<Pokemon>> = Transformations.map(database.pokemonDao.getPokes()){
        it.asDomainModel()
    }

    suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO){
                val pokeList = PokeApi.retrofitService.getPokes(page).await()
                database.pokemonDao.insertAll(*pokeList.asDatabaseModel()!!)
        }
    }
}