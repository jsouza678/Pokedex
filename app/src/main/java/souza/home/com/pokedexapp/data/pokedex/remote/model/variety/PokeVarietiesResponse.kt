package souza.home.com.pokedexapp.data.pokedex.remote.model.variety

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokeVarietiesResponse(
    @Json(name="id")
    var _id: String,
    var evolution_chain: PokeEvolutionPath,
    var varieties: MutableList<PokeVarieties>,
    var color: PokeColor,
    @Json(name="flavor_text_entries")
    var description: MutableList<PokeFlavorDescription>
)

