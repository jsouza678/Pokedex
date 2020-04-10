package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.data.pokedex.remote.model.Poke

interface PokemonRepository {

    val pokes: LiveData<List<Poke>>

    suspend fun refreshPokes(page: Int)

}