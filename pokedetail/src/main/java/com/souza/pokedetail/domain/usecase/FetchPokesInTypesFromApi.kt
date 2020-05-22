package com.souza.pokedetail.domain.usecase

import com.souza.pokedetail.domain.repository.TypeRepository

class FetchPokesInTypesFromApi(private val typeRepository: TypeRepository) {
    suspend operator fun invoke(id: Int) = typeRepository.refreshTypes(id)
}
