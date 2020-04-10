package souza.home.com.pokedexapp.data.pokedex

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety

interface VarietiesRepository {

    val varieties : LiveData<PokeVariety>?
    suspend fun refreshVarieties(id: String)
}


