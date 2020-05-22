package com.souza.pokedetail.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.souza.pokedetail.utils.Constants.Companion.ABILITY_TABLE_NAME

@Entity(tableName = ABILITY_TABLE_NAME)
data class AbilityEntity(
    @PrimaryKey
    val _id: Int,
    val effect: String?
)
