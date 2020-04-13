package souza.home.com.pokedexapp.data.pokedex.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.data.pokedex.local.model.*

@Database(entities = [PokemonEntity::class,
    VarietyEntity::class,
    PropertyEntity::class,
    AbilityEntity::class,
    TypeEntity::class,
    EvolutionEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val varietiesDao: VarietiesDao
    abstract val propertyDao: PropertyDao
    abstract val abilitiesDao: AbilitiesDao
    abstract val evolutionChainDao: EvolutionChainDao
    abstract val typesDao: TypesDao

    companion object{
        private lateinit var INSTANCE : PokemonDatabase

        fun getDatabase(context: Context): PokemonDatabase {
            synchronized(PokemonDatabase::class.java){
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PokemonDatabase::class.java,
                        "pokedex3.db").build() // name
                }
            }
            return INSTANCE
        }
    }
}
