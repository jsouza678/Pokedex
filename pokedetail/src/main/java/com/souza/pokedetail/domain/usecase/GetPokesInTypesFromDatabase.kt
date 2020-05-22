package com.souza.pokedetail.domain.usecase

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeType
import com.souza.pokedetail.domain.repository.TypeRepository

class GetPokesInTypesFromDatabase(private val typeRepository: TypeRepository) {
    operator fun invoke(id: Int): LiveData<PokeType>? = typeRepository.getPokesInType(id)
}
