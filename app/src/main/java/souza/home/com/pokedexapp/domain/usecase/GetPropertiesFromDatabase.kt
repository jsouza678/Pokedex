package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.repository.PropertyRepository

class GetPropertiesFromDatabase(private val propertyRepository: PropertyRepository) {
    operator fun invoke(id: Int): LiveData<PokeProperty>? = propertyRepository.getProperties(id)
}
