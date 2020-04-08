package souza.home.com.pokedexapp.data.remote.model

import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeRootProperty

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    val name: String,
    val url: String
)

fun PokeRootProperty.asDomainModel() : MutableList<PokemonResponse>? {
    return results?.map{
        PokemonResponse(
            url = it.url,
            name = it.name
        )
    }?.toMutableList()
}

fun PokeRootProperty.asDatabaseModel() : Array<Pokemon>? {
    return results?.map {
        Pokemon(
            url = it.url,
            name = it.name
        )
    }?.toTypedArray()
}