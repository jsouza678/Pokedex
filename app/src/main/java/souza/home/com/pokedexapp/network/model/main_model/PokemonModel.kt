package souza.home.com.pokedexapp.network.model.main_model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabasePokedex constructor(
    @PrimaryKey
    val url: String,
    val name: String)

fun List<DatabasePokedex>.asDomainModel(): List<Pokemon>{
    return map{
        Pokemon(
            url = it.url,
            name = it.name
        )
    }
}