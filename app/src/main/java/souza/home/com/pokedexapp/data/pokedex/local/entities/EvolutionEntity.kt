package souza.home.com.pokedexapp.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.EVOLUTION_TABLE_NAME

@Entity(tableName = EVOLUTION_TABLE_NAME)
data class EvolutionEntity constructor(
    @PrimaryKey
    val _id: Int,
    val evolution: String?
)
