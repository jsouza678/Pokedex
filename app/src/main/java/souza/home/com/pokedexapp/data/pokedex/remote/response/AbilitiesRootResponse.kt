package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbilitiesRootResponse(
    @Json(name = "id")
    val _id: Int,
    @Json(name = "effect_entries")
    val effect: MutableList<AbilitiesDescription>?
)
