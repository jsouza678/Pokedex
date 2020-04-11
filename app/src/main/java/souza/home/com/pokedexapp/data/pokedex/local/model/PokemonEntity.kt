package souza.home.com.pokedexapp.data.pokedex.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_TABLE_NAME

@Entity (tableName = POKE_TABLE_NAME)
data class PokemonEntity constructor(
    @PrimaryKey
    val _id: String,
    val name: String
)
