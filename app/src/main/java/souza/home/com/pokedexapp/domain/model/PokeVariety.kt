package souza.home.com.pokedexapp.domain.model

import com.squareup.moshi.Json
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.PokeColor
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.PokeEvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.PokeVarieties

data class PokeVariety(
    var _id: String?,
    var evolution_chain: PokeEvolutionPath?,
    var varieties: MutableList<PokeVarieties>?,
    var color: PokeColor?,
    @Json(name="flavor_text_entries")
    var description: String?
)