package souza.home.com.pokedexapp.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.data.pokedex.remote.model.Poke
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_TABLE_NAME
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse

@Entity (tableName = POKE_TABLE_NAME)
data class PokemonEntity constructor(
    @PrimaryKey
    val _id: String,
    val name: String
)

fun List<PokemonResponse>.asDomainModel(): List<Poke>{
    Log.i("teste" , "data converted! to Model1")
    return map{
        Poke(
            url = it._id,
            name = it.name
        )
    }
}