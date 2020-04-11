package souza.home.com.pokedexapp.data.pokedex.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.PROPERTY_TABLE_NAME

@Entity (tableName = PROPERTY_TABLE_NAME)
data class PropertyEntity constructor (
    @PrimaryKey
    var id : Int, // change to string // was int
    var abilities : String, // was mutable list of poke abilitits
    var name: String,
    var height: Int,
    var sprites: String, // was pokesprites
    var stats: String, // was list of pokestats
    var types: String, // was mutable list of types
    var weight: Int = 0
)