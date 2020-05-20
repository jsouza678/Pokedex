package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypesRootResponse(
    @Json(name = "id")
    val _id: Int,
    val pokemon: MutableList<TypeResponse>?
)
