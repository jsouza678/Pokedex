package com.souza.pokedetail.domain.usecase

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.domain.repository.VarietyRepository

class GetVarietiesFromDatabase(private val varietyRepository: VarietyRepository) {
    operator fun invoke(id: Int): LiveData<PokeVariety?>? = varietyRepository.getVarieties(id)
}
