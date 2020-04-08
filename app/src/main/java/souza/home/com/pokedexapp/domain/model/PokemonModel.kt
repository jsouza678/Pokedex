package souza.home.com.pokedexapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse

@Entity
data class Pokemon constructor(
    @PrimaryKey
    val url: String,
    val name: String)

fun List<PokemonResponse>.asDomainModel(): List<PokemonResponse>{
    return map{
        PokemonResponse(
            url = it.url,
            name = it.name
        )
    }
}