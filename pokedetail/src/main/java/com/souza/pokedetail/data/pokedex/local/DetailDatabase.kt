package com.souza.pokedetail.data.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.souza.pokedetail.data.pokedex.local.entities.AbilityEntity
import com.souza.pokedetail.data.pokedex.local.entities.EvolutionEntity
import com.souza.pokedetail.data.pokedex.local.entities.PropertyEntity
import com.souza.pokedetail.data.pokedex.local.entities.TypeEntity
import com.souza.pokedetail.data.pokedex.local.entities.VarietyEntity

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
