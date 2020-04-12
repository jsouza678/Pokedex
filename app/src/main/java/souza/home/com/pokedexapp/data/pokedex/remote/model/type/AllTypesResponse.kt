package souza.home.com.pokedexapp.data.pokedex.remote.model.type

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.NestedType

@JsonClass(generateAdapter = true)
data class AllTypesResponse(
    @Json(name = "id")
    var _id: Int,
    var pokemon: MutableList<NestedType>?
)