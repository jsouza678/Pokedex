package souza.home.com.pokedexapp.data.pokedex.remote.model

import com.squareup.moshi.Json
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeColor
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeEvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeFlavorDescription
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarieties

data class PokeVariety(
    var _id: String?,
    var evolution_chain: PokeEvolutionPath?,
    var varieties: MutableList<PokeVarieties>?,
    var color: PokeColor?,
    @Json(name="flavor_text_entries")
    var description: MutableList<PokeFlavorDescription>?
)