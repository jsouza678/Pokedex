package souza.home.com.pokedexapp.data.pokedex.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse

@JsonClass(generateAdapter = true)
data class PokeRootProperty(
    @Json(name = "results")
    var results : MutableList<PokemonResponse>? = null
)