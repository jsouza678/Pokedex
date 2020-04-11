package souza.home.com.pokedexapp.data.pokedex.remote.model.stat

import com.squareup.moshi.Json

data class PokeNestedStat(
    @Json(name="name")
    var stat: String
)