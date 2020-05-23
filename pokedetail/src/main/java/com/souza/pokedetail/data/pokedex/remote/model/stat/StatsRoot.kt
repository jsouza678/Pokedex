package com.souza.pokedetail.data.pokedex.remote.model.stat

import com.squareup.moshi.Json

data class StatsRoot(
    @Json(name = "base_stat")
    val baseStat: String?,
    val effort: String?,
    val stats: Stat?
)
