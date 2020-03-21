package souza.home.com.pokedexapp.network

import com.squareup.moshi.Json

data class PokeRootProperty(
    @Json(name = "results")
    var results : MutableList<PokeProperty>? = null
)