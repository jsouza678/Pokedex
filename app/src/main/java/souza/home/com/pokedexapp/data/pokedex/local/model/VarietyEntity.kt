package souza.home.com.pokedexapp.data.pokedex.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.VARIETY_TABLE_NAME

@Entity (tableName = VARIETY_TABLE_NAME)
data class PokeVariationsEntity constructor(
    @PrimaryKey
    val _poke_variety_id: String,
    val evolution_chain: String,
    val varieties: String,
    val color: String,
    val description: String
)
