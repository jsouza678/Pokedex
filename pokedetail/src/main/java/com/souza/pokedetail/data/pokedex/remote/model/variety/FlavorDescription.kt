package com.souza.pokedetail.data.pokedex.remote.model.variety

import com.squareup.moshi.Json

data class FlavorDescription(
    @Json(name = "flavor_text")
    val flavorText: String?,
    val language: Language?
)
