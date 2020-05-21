package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.repository.VarietyRepository

class GetVarietiesFromDatabase(private val varietyRepository: VarietyRepository) {
    operator fun invoke(id: Int): LiveData<PokeVariety?>? = varietyRepository.getVarieties(id)
}
