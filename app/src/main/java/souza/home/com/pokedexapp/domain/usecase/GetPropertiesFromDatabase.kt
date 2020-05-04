package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.repository.PropertiesRepository

class GetPropertiesFromDatabase (private val propertiesRepository: PropertiesRepository) {
    operator fun invoke(id: Int): LiveData<PokeProperty>? = propertiesRepository.getProperties(id)
}
