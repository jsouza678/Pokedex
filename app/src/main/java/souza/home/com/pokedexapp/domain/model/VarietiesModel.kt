package souza.home.com.pokedexapp.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.data.pokedex.utils.TypeConverter
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarietiesResponse

@Entity
data class PokeVariations constructor(
    @PrimaryKey
    val id: Int?,
    val evolution_chain: String?,
    val varieties: String?, //MutableList<String>,
    val color: String?,
    val description: String?
)

fun PokeVariations.asDomainModelFromVariations(): PokeVariety {

    val pokeEvolutionPathAsObject = TypeConverter.ToEvolutionPath(evolution_chain)
    val pokeVarietiesAsList = TypeConverter.ToVarietiesList(varieties)
    val pokeColorAsObject = TypeConverter.ToColor(color)
    val pokeDescriptionAsList = TypeConverter.ToDescriptionList(description)

    Log.i("teste" , "data converted! to Domain")

    return PokeVariety(
        id = id,
        evolution_chain = pokeEvolutionPathAsObject!!,
        varieties = pokeVarietiesAsList!!,
        color = pokeColorAsObject!!,
        description = pokeDescriptionAsList!!
    )
}

fun PokeVarietiesResponse?.asDomainModelFromVariationsdd(): PokeVariety {

    val pokeEvolutionPathAsObject = (this?.evolution_chain)
    val pokeVarietiesAsList = (this?.varieties)
    val pokeColorAsObject = (this?.color)
    val pokeDescriptionAsList = (this?.description)

    Log.i("teste" , "data converted! to Domain")

     return PokeVariety(
         id = this?.id!!,
         evolution_chain = pokeEvolutionPathAsObject!!,
         varieties = pokeVarietiesAsList!!,
         color = pokeColorAsObject!!,
         description = pokeDescriptionAsList!!
     )
}