package souza.home.com.pokedexapp.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.TypeConverter
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarietiesResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.VARIETY_TABLE_NAME


@Entity (tableName = VARIETY_TABLE_NAME, foreignKeys = [ForeignKey(entity = PokemonEntity::class,
                                    parentColumns = [ "_id" ],
                                    childColumns = [ "_poke_variety_id" ],
                                    onDelete = ForeignKey.CASCADE )])
data class PokeVariationsEntity constructor(
    @PrimaryKey
    val _poke_variety_id: String,
    val evolution_chain: String,
    val varieties: String,
    val color: String,
    val description: String
)

fun PokeVariationsEntity.asDomainModelFromVariations(): PokeVariety {

    val pokeEvolutionPathAsObject = TypeConverter.ToEvolutionPath(evolution_chain)
    val pokeVarietiesAsList = TypeConverter.ToVarietiesList(varieties)
    val pokeColorAsObject = TypeConverter.ToColor(color)
    val pokeDescriptionAsList = TypeConverter.ToDescriptionList(description)

    Log.i("teste" , "data converted! to Domain")

    return PokeVariety(
        _id = _poke_variety_id,
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
         _id = this?._id!!,
         evolution_chain = pokeEvolutionPathAsObject!!,
         varieties = pokeVarietiesAsList!!,
         color = pokeColorAsObject!!,
         description = pokeDescriptionAsList!!
     )
}