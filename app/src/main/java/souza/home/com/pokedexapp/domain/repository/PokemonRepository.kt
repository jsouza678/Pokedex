package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.Pokemon

interface PokemonRepository {

    fun getPokes(): LiveData<List<Pokemon>?>

    suspend fun refreshPokes(page: Int)
}
