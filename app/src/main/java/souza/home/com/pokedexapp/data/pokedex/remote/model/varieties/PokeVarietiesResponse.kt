package souza.home.com.pokedexapp.data.pokedex.remote.model.varieties

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.data.pokedex.utils.TypeConverter
import souza.home.com.pokedexapp.domain.model.PokeVariations

@JsonClass(generateAdapter = true)
data class PokeVarietiesResponse(
    var id: Int?,
    var evolution_chain: PokeEvolutionPath?,
    var varieties: MutableList<PokeVarieties>?,
    var color: PokeColor?,
    @Json(name="flavor_text_entries")
    var description: MutableList<PokeFlavorDescription>?
)


// goes to BD
fun PokeVarietiesResponse.asDatabaseModel() : PokeVariations {
    //val evolutionChainAsString = TypeConverter.FromPokeEvolutionPath(evolution_chain)
    val pokeVarietiesAsString = TypeConverter.fromVarieties(varieties)
    val pokeDescriptionAsString = TypeConverter.fromDescription(description)
    val pokeEvolutionChainAsString = TypeConverter.fromEvolutionChain(evolution_chain)
    val pokeColorAsString = TypeConverter.fromColor(color)

    Log.i("teste" , "data converted! to DB")

    return PokeVariations(
            id = id,
            evolution_chain = pokeEvolutionChainAsString!!,
            varieties = pokeVarietiesAsString!!,
            color = pokeColorAsString!!,
            description = pokeDescriptionAsString!!
    )
}

