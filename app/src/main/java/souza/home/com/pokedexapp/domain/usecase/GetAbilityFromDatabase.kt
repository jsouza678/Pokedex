package souza.home.com.pokedexapp.domain.usecase

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.domain.repository.AbilityRepository

class GetAbilityFromDatabase(private val abilityRepository: AbilityRepository) {
    operator fun invoke(id: Int): LiveData<String>? = abilityRepository.getAbilityDescription(id)
}
