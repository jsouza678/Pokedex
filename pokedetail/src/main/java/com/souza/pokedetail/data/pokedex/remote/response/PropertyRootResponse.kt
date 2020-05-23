package com.souza.pokedetail.data.pokedex.remote.response

import com.souza.pokedetail.data.pokedex.remote.model.ability.AbilitiesRoot
import com.souza.pokedetail.data.pokedex.remote.model.stat.Sprites
import com.souza.pokedetail.data.pokedex.remote.model.stat.StatsRoot
import com.souza.pokedetail.data.pokedex.remote.model.type.TypeRoot
import com.souza.pokedetail.utils.Constants.Companion.EMPTY_STRING
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PropertyRootResponse(
    val id: Int,
    val abilities: MutableList<AbilitiesRoot>?,
    val name: String = EMPTY_STRING,
    val height: Int?,
    val sprites: Sprites?,
    val stats: List<StatsRoot>?,
    val types: MutableList<TypeRoot>?,
    val weight: Int?
)
