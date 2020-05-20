package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.Sprites
import souza.home.com.pokedexapp.data.pokedex.remote.model.stat.StatsRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.TypeRoot
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

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
