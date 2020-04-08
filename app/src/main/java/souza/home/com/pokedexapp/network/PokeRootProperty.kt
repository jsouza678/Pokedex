package souza.home.com.pokedexapp.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.network.model.main_model.Pokemon

@JsonClass(generateAdapter = true)
data class PokeRootProperty(
    @Json(name = "results")
    var results : MutableList<Pokemon>? = null
)