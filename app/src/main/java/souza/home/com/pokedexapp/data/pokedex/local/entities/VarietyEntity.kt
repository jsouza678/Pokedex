package souza.home.com.pokedexapp.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.VARIETY_TABLE_NAME

@Entity(tableName = VARIETY_TABLE_NAME)
data class VarietyEntity constructor(
    @PrimaryKey
    val _poke_variety_id: Int,
    val evolution_chain: String?,
    val varieties: String?,
    val color: String?,
    val description: String?
)
