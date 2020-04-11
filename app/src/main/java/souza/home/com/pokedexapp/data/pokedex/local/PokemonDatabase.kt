package souza.home.com.pokedexapp.data.pokedex.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.data.pokedex.local.model.VarietyEntity
import souza.home.com.pokedexapp.data.pokedex.local.model.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.local.model.PropertyEntity

@Database(entities = [PokemonEntity::class , VarietyEntity::class, PropertyEntity::class ], version = 1)
abstract class PokemonsDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val varietiesDao: VarietiesDao
    abstract val propertyDAO: PropertyDAO

    companion object{
        private lateinit var INSTANCE : PokemonsDatabase

        fun getDatabase(context: Context): PokemonsDatabase {
            synchronized(PokemonsDatabase::class.java){
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PokemonsDatabase::class.java,
                        "poksaesodex.db").build() // name
                }
            }
            return INSTANCE
        }
    }
}
