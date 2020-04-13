package souza.home.com.pokedexapp.data.pokedex.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.data.pokedex.local.model.EvolutionEntity
import souza.home.com.pokedexapp.data.pokedex.local.model.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.local.model.PropertyEntity
import souza.home.com.pokedexapp.data.pokedex.local.model.VarietyEntity

@Database(entities = [PokemonEntity::class,
    VarietyEntity::class,
    PropertyEntity::class,
    EvolutionEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val varietiesDao: VarietiesDao
    abstract val propertyDao: PropertyDao
    abstract val evolutionChainDao: EvolutionChainDao

    companion object{
        private lateinit var INSTANCE : PokemonDatabase

        fun getDatabase(context: Context): PokemonDatabase {
            synchronized(PokemonDatabase::class.java){
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PokemonDatabase::class.java,
                        "pokedex.db").build() // name
                }
            }
            return INSTANCE
        }
    }
}