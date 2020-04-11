package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety

interface PropertiesRepository{

    val properties : LiveData<PokeProperty>?

    suspend fun refreshProperties(id: Int)
}


