package com.souza.pokedetail.domain.usecase

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.repository.PropertyRepository

class GetPropertiesFromDatabase(private val propertyRepository: PropertyRepository) {
    operator fun invoke(id: Int): LiveData<PokeProperty>? = propertyRepository.getProperties(id)
}
