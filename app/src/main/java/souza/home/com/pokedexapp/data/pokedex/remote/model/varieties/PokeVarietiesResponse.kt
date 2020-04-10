package souza.home.com.pokedexapp.data.pokedex.remote.model.varieties

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.utils.TypeConverter
import souza.home.com.pokedexapp.domain.model.PokeVariationsEntity

@JsonClass(generateAdapter = true)
data class PokeVarietiesResponse(
    @Json(name="id")
    var _id: String,
    var evolution_chain: PokeEvolutionPath,
    var varieties: MutableList<PokeVarieties>,
    var color: PokeColor,
    @Json(name="flavor_text_entries")
    var description: MutableList<PokeFlavorDescription>
)


// goes to BD
fun PokeVarietiesResponse.asDatabaseModel() : PokeVariationsEntity {
    //val evolutionChainAsString = TypeConverter.FromPokeEvolutionPath(evolution_chain)
    val pokeVarietiesAsString = TypeConverter.fromVarieties(varieties)
    val pokeDescriptionAsString = TypeConverter.fromDescription(description)
    val pokeEvolutionChainAsString = TypeConverter.fromEvolutionChain(evolution_chain)
    val pokeColorAsString = TypeConverter.fromColor(color)

    Log.i("teste" , "data converted! to DB")

    return PokeVariationsEntity(
            _poke_variety_id = _id,
            evolution_chain = pokeEvolutionChainAsString!!,
            varieties = pokeVarietiesAsString!!,
            color = pokeColorAsString!!,
            description = pokeDescriptionAsString!!
    )
}

