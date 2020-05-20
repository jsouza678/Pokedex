package souza.home.com.pokedexapp.domain.model

import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.Sprites
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.StatsRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.TypeRoot

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
