package souza.home.com.pokedexapp.data.pokedex.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.PROPERTY_TABLE_NAME

@Entity(tableName = PROPERTY_TABLE_NAME)
data class PropertyEntity constructor (
    @PrimaryKey
    var id: Int,
    var abilities: String?,
    var name: String?,
    var height: Int?,
    var sprites: String?,
    var stats: String?,
    var types: String?,
    var weight: Int?
)
