package souza.home.com.pokedexapp.data.pokedex.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.ABILITY_TABLE_NAME

@Entity (tableName = ABILITY_TABLE_NAME)
class AbilityEntity constructor(
    @PrimaryKey
    val _id: Int,
    var effect: String?
)