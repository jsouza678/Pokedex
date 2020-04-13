package souza.home.com.pokedexapp.data.pokedex.remote.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Color
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.EvolutionPath
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.FlavorDescription
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties

@JsonClass(generateAdapter = true)
data class VarietiesResponse(
    @Json(name="id")
    var _id: String,
    var evolution_chain: EvolutionPath,
    var varieties: MutableList<Varieties>,
    var color: Color,
    @Json(name="flavor_text_entries")
    var description: MutableList<FlavorDescription>
)