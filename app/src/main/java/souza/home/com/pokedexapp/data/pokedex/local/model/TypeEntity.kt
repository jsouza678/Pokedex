package souza.home.com.pokedexapp.data.pokedex.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.TYPE_TABLE_NAME

@Entity (tableName = TYPE_TABLE_NAME)
data class TypeEntity constructor (
    @PrimaryKey
    val _id: Int,
    var types: String?
)