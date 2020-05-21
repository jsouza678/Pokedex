package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolutionchain.EvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Color
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.FlavorDescription
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties

@JsonClass(generateAdapter = true)
data class VarietiesRootResponse(
    val id: String,
    @Json(name = "evolution_chain")
    val evolutionChain: EvolutionPath?,
    val varieties: MutableList<Varieties>?,
    val color: Color?,
    @Json(name = "flavor_text_entries")
    val description: MutableList<FlavorDescription>?
)
