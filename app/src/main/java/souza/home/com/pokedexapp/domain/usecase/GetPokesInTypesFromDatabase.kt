package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeType
import souza.home.com.pokedexapp.domain.repository.TypeRepository

class GetPokesInTypesFromDatabase(private val typeRepository: TypeRepository) {
    operator fun invoke(id: Int): LiveData<PokeType>? = typeRepository.getPokesInType(id)
}
