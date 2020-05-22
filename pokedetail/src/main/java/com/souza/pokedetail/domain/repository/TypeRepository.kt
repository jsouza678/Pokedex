package com.souza.pokedetail.domain.repository

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeType

interface TypeRepository {

    fun getPokesInType(id: Int): LiveData<PokeType>?

    suspend fun refreshTypes(id: Int)
}
