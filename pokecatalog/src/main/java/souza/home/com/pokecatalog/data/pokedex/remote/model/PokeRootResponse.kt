package souza.home.com.pokecatalog.data.pokedex.remote.model

import com.squareup.moshi.JsonClass
import com.souza.pokedetail.data.pokedex.remote.response.PokemonResponse

@JsonClass(generateAdapter = true)
data class PokeRootResponse(
    val results: MutableList<PokemonResponse>?
)
