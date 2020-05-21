package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolutionchain.Evolution

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    val id: Int,
    val chain: Evolution?
)
