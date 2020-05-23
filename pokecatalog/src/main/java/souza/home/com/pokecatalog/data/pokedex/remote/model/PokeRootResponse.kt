package souza.home.com.pokecatalog.data.pokedex.remote.model

import com.souza.pokedetail.data.pokedex.remote.response.PokemonResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokeRootResponse(
    val results: MutableList<PokemonResponse>?
)
