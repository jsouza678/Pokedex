package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeVariety

interface VarietiesRepository {

    val varieties : LiveData<PokeVariety>?
    suspend fun refreshVarieties(id: Int)
}


