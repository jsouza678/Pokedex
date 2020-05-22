package com.souza.pokedetail.domain.repository

import androidx.lifecycle.LiveData
import com.souza.pokedetail.domain.model.PokeVariety

interface VarietyRepository {

    fun getVarieties(id: Int): LiveData<PokeVariety?>?

    suspend fun refreshVarieties(id: Int)
}
