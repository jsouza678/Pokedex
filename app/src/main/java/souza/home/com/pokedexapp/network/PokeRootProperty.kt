package souza.home.com.pokedexapp.network

import com.squareup.moshi.Json
import souza.home.com.pokedexapp.network.main_model.Pokemon

data class PokeRootProperty(
    @Json(name = "results")
    var results : MutableList<Pokemon>? = null
)