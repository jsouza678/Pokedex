package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeType

interface TypesRepository {

    val types : LiveData<PokeType>?

    suspend fun refreshtypes(id: Int)

}