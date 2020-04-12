package souza.home.com.pokedexapp.data.pokedex.remote.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.Evolution

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    @Json(name ="id")
    var _id: Int,
    var chain: Evolution
)