package com.souza.pokedetail.domain.usecase

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.repository.AbilityRepository

class GetAbilityFromDatabase(private val abilityRepository: AbilityRepository) {
    operator fun invoke(id: Int): LiveData<String>? = abilityRepository.getAbilityDescription(id)
}
