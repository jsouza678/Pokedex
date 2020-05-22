package com.souza.pokedetail.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.souza.pokedetail.utils.Constants.Companion.TYPE_TABLE_NAME

@Entity(tableName = TYPE_TABLE_NAME)
data class TypeEntity(
    @PrimaryKey
    val _id: Int,
    val pokemon: String?
)
