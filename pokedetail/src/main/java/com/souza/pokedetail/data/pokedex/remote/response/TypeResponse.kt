package com.souza.pokedetail.data.pokedex.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeResponse(
    val pokemon: PokemonResponse?
)
