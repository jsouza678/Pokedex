package com.souza.pokedetail.domain.model

import com.souza.pokedetail.data.pokedex.remote.response.TypeResponse

data class PokeType(
    val id: Int,
    val pokesInTypes: MutableList<TypeResponse>?
)
