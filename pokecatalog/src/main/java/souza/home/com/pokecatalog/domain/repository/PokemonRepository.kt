package souza.home.com.pokecatalog.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokecatalog.domain.model.Pokemon

interface PokemonRepository {

    fun getPokes(): LiveData<List<Pokemon>?>

    suspend fun refreshPokes(page: Int)
}
