package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllTypesResponse(
    @Json(name = "id")
    var _id: Int,
    var pokemon: MutableList<NestedTypeResponse>?
)
