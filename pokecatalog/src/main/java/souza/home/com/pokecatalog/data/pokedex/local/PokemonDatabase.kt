package souza.home.com.pokecatalog.data.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import souza.home.com.pokecatalog.data.pokedex.local.entities.PokemonEntity

@Database(entities = [
    PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}
