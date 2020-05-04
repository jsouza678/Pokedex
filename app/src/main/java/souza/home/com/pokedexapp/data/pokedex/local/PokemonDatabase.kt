package souza.home.com.pokedexapp.data.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import souza.home.com.pokedexapp.data.pokedex.local.entities.EvolutionEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PropertyEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.VarietyEntity

@Database(entities = [PokemonEntity::class,
    VarietyEntity::class,
    PropertyEntity::class,
    EvolutionEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val varietiesDao: VarietiesDao
    abstract val propertyDao: PropertyDao
    abstract val evolutionChainDao: EvolutionChainDao
}
