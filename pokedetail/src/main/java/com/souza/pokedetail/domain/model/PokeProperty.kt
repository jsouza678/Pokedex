package com.souza.pokedetail.domain.model

import com.souza.pokedetail.data.pokedex.remote.model.ability.AbilitiesRoot
import com.souza.pokedetail.data.pokedex.remote.model.stat.Sprites
import com.souza.pokedetail.data.pokedex.remote.model.stat.StatsRoot
import com.souza.pokedetail.data.pokedex.remote.model.type.TypeRoot

data class PokeProperty(
    val id: Int,
    val abilities: MutableList<AbilitiesRoot>?,
    val name: String?,
    val height: Int?,
    val sprites: Sprites?,
    val stats: List<StatsRoot>?,
    val types: MutableList<TypeRoot>?,
    val weight: Int?
)
