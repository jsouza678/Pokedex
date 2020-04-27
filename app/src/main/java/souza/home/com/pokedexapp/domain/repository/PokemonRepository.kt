package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.Poke

interface PokemonRepository {

    fun getAllPokes(): LiveData<List<Poke>?>

    suspend fun refreshPokes(page: Int)
}
