package souza.home.com.pokedexapp.domain.model

import com.squareup.moshi.Json
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Color
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.EvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties

data class PokeVariety(
    var _id: Int,
    var evolution_chain: EvolutionPath?,
    var varieties: MutableList<Varieties>?,
    var color: Color?,
    @Json(name = "flavor_text_entries")
    var description: String?
)
