package souza.home.com.pokedexapp.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.utils.Constants.Companion.TYPE_TABLE_NAME

@Entity(tableName = TYPE_TABLE_NAME)
data class TypeEntity(
    @PrimaryKey
    val _id: Int,
    val pokemon: String?
)
