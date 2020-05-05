package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.repository.VarietiesRepository

class GetVarietiesFromDatabase(private val varietiesRepository: VarietiesRepository) {
    operator fun invoke(id: Int): LiveData<PokeVariety?>? = varietiesRepository.getVarieties(id)
}
