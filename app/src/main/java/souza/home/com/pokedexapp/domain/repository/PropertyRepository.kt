package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeProperty

interface PropertyRepository {

    fun getProperties(id: Int): LiveData<PokeProperty>?

    suspend fun refreshProperties(id: Int)
}
