package souza.home.com.pokedexapp.network.model.main_model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.network.PokeRootProperty

@JsonClass(generateAdapter = true)
data class Pokemon(
    val url: String,
    val name: String
)

fun PokeRootProperty.asDomainModel() : MutableList<Pokemon>? {
    return results?.map{
        Pokemon(
            url = it.url,
            name = it.name
        )
    }?.toMutableList()

}

fun PokeRootProperty.asDatabaseModel() : Array<DatabasePokedex>? {
    return results?.map {
        DatabasePokedex(
            url = it.url,
            name = it.name)
    }?.toTypedArray()
}