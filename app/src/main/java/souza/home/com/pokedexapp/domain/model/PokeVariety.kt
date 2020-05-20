package souza.home.com.pokedexapp.domain.model

import souza.home.com.pokedexapp.data.pokedex.remote.model.evolutionchain.EvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Color
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties

data class PokeVariety(
    val id: Int,
    val evolutionChain: EvolutionPath?,
    val varieties: MutableList<Varieties>?,
    val color: Color?,
    val description: String?
)
