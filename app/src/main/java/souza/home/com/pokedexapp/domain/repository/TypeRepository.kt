package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeType

interface TypeRepository {

    fun getPokesInType(id: Int): LiveData<PokeType>?

    suspend fun refreshTypes(id: Int)
}
