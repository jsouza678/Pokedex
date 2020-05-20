package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolutionchain.Evolution

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    @Json(name = "id")
    val _id: Int,
    val chain: Evolution?
)
