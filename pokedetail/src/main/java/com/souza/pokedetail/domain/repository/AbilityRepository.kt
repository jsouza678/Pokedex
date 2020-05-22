package com.souza.pokedetail.domain.repository

import androidx.lifecycle.LiveData

interface AbilityRepository {

    fun getAbilityDescription(id: Int): LiveData<String>?

    suspend fun refreshAbilities(id: Int)
}
