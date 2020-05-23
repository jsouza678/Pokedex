package com.souza.pokedetail.data.pokedex.remote.response

import com.squareup.moshi.Json

data class PokemonResponse(
    @Json(name = "url")
    val id: String,
    val name: String?
)
