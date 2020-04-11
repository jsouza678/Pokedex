package souza.home.com.pokedexapp.data.pokedex.remote.model.stat

import com.squareup.moshi.Json

data class NestedStat(
    @Json(name="name")
    var stat: String
)