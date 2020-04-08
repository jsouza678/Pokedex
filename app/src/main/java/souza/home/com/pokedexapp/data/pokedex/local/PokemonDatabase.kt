package souza.home.com.pokedexapp.data.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.domain.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonsDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}