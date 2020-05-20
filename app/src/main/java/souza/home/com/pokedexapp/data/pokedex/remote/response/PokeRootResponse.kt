package souza.home.com.pokedexapp.data.pokedex.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokeRootResponse(
    val results: MutableList<PokemonResponse>?
)
