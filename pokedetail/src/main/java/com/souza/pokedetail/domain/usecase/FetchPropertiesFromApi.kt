package com.souza.pokedetail.domain.usecase

import com.souza.pokedetail.domain.repository.PropertyRepository

class FetchPropertiesFromApi(private val propertyRepository: PropertyRepository) {
    suspend operator fun invoke(id: Int) = propertyRepository.refreshProperties(id)
}
