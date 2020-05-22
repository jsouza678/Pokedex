package com.souza.pokedetail.domain.repository

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeProperty

interface PropertyRepository {

    fun getProperties(id: Int): LiveData<PokeProperty>?

    suspend fun refreshProperties(id: Int)
}
