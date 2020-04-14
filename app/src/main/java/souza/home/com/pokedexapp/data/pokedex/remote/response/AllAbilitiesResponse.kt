package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllAbilitiesResponse(
    @Json(name = "id")
    var _id: Int,
    @Json(name = "effect_entries")
    var effect: MutableList<AbilitiesDescription>?
)
