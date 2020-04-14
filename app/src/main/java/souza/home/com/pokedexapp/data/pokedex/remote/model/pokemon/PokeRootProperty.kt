package souza.home.com.pokedexapp.data.pokedex.remote.model.pokemon

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.PokemonResponse

@JsonClass(generateAdapter = true)
data class PokeRootProperty(
    @Json(name = "results")
    var results: MutableList<PokemonResponse>? = null
)
