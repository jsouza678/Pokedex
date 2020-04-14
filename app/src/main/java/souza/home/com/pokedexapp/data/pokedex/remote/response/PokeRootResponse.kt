package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokeRootResponse(
    @Json(name = "results")
    var results: MutableList<PokemonResponse>? = null
)
