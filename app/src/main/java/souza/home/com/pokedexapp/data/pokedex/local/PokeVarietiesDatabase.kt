package souza.home.com.pokedexapp.data.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.domain.model.PokeVariations

@Database(entities = [PokeVariations::class], version = 1)
abstract class PokeVarietiesDatabase: RoomDatabase() {
    abstract val varietiesDao: VarietiesDao
}