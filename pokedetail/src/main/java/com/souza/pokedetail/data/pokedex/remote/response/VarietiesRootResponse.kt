package com.souza.pokedetail.data.pokedex.remote.response

import com.souza.pokedetail.data.pokedex.remote.model.evolutionchain.EvolutionPath
import com.souza.pokedetail.data.pokedex.remote.model.variety.Color
import com.souza.pokedetail.data.pokedex.remote.model.variety.FlavorDescription
import com.souza.pokedetail.data.pokedex.remote.model.variety.Varieties
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VarietiesRootResponse(
    val id: String,
    @Json(name = "evolution_chain")
    val evolutionChain: EvolutionPath?,
    val varieties: MutableList<Varieties>?,
    val color: Color?,
    @Json(name = "flavor_text_entries")
    val description: MutableList<FlavorDescription>?
)
