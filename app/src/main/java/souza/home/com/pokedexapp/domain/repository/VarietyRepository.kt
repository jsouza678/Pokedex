package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeVariety

interface VarietyRepository {

    fun getVarieties(id: Int): LiveData<PokeVariety?>?

    suspend fun refreshVarieties(id: Int)
}
