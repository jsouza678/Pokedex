package com.souza.pokedetail.domain.model

import com.souza.pokedetail.data.pokedex.remote.model.evolutionchain.EvolutionPath
import com.souza.pokedetail.data.pokedex.remote.model.variety.Color
import com.souza.pokedetail.data.pokedex.remote.model.variety.Varieties

data class PokeVariety(
    val id: Int,
    val evolutionChain: EvolutionPath?,
    val varieties: MutableList<Varieties>?,
    val color: Color?,
    val description: String?
)
