package com.souza.pokedetail.data.pokedex.remote.model.stat

import com.squareup.moshi.Json

data class Stat(
    @Json(name = "name")
    val stat: String?
)
