package com.souza.pokedetail.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.souza.pokedetail.utils.Constants.Companion.EVOLUTION_TABLE_NAME

@Entity(tableName = EVOLUTION_TABLE_NAME)
data class EvolutionEntity constructor(
    @PrimaryKey
    val _id: Int,
    val evolution: String?
)
