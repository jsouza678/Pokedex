package souza.home.com.pokedexapp.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.ABILITY_TABLE_NAME

@Entity(tableName = ABILITY_TABLE_NAME)
data class AbilityEntity(
    @PrimaryKey
    val _id: Int,
    val effect: String?
)
