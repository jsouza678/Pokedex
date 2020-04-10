package souza.home.com.pokedexapp.data.remote.model

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.domain.model.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeRootProperty
import souza.home.com.pokedexapp.utils.cropPokeUrl

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    @Json(name="url")
    val _id: String,
    val name: String
)

fun PokeRootProperty.asDomainModel() : MutableList<PokemonResponse>? {
    return results?.map{
        PokemonResponse(
            _id = it._id,
            name = it.name
        )
    }?.toMutableList()
}

fun PokeRootProperty.asDatabaseModel() : Array<PokemonEntity>? {
    Log.i("teste" , "data converted! to DB1")
    return results?.map {
        PokemonEntity(
            _id = cropPokeUrl(it._id),
            name = it.name
        )
    }?.toTypedArray()
}