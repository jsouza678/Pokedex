package souza.home.com.pokedexapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name="url")
    val _id: String,
    val name: String
)

