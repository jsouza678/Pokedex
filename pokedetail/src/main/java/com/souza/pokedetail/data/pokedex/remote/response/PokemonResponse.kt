package com.souza.pokedetail.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class PokemonResponse(
    @Json(name = "url")
    val id: String,
    val name: String?
)
