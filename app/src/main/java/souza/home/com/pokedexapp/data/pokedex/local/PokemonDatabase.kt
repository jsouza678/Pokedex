package souza.home.com.pokedexapp.data.pokedex.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.domain.model.PokeVariationsEntity
import souza.home.com.pokedexapp.domain.model.PokemonEntity

// 2 entities at the moment
@Database(entities = [PokemonEntity::class , PokeVariationsEntity::class ], version = 1)
abstract class PokemonsDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val varietiesDao: VarietiesDao

    companion object{
        private lateinit var INSTANCE : PokemonsDatabase

        fun getDatabase(context: Context): PokemonsDatabase {
            synchronized(PokemonsDatabase::class.java){
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PokemonsDatabase::class.java,
                        "psssokess.db").build() // name
                }
            }
            return INSTANCE
        }
    }
}
