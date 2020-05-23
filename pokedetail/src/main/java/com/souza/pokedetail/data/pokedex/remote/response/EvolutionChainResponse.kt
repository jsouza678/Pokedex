package com.souza.pokedetail.data.pokedex.remote.response

import com.souza.pokedetail.data.pokedex.remote.model.evolutionchain.Evolution
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    val id: Int,
    val chain: Evolution?
)
