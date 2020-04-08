package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.domain.model.asDomainModel
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse
import souza.home.com.pokedexapp.data.remote.model.asDatabaseModel
import souza.home.com.pokedexapp.data.pokedex.local.PokemonsDatabase

class PokemonRepositoryImpl(private val database: PokemonsDatabase) {

    val pokes: LiveData<List<PokemonResponse>> = Transformations.map(database.pokemonDao.getPokes()){
        it.asDomainModel()
    }

    suspend fun refreshPokes(page: Int) {
        withContext(Dispatchers.IO){
                val pokeList = PokeApi.retrofitService.getPokes(page).await()
                database.pokemonDao.insertAll(*pokeList.asDatabaseModel()!!)
        }
    }
}