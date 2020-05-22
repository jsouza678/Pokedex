package com.souza.pokedetail.data.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.souza.pokedetail.data.pokedex.local.entities.*

@Database(entities = [
    VarietyEntity::class,
    PropertyEntity::class,
    EvolutionEntity::class,
    AbilityEntity::class,
    TypeEntity::class], version = 1)
abstract class DetailDatabase : RoomDatabase() {
    abstract val varietiesDao: VarietiesDao
    abstract val propertyDao: PropertyDao
    abstract val evolutionChainDao: EvolutionChainDao
    abstract val abilityDao: AbilityDao
    abstract val typeDao: TypeDao
}
