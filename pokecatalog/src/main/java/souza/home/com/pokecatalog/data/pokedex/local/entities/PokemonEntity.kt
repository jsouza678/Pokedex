package souza.home.com.pokecatalog.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokecatalog.utils.Constants.Companion.POKE_TABLE_NAME

@Entity(tableName = POKE_TABLE_NAME)
data class PokemonEntity constructor(
    @PrimaryKey
    val _id: Int,
    val name: String?
)
