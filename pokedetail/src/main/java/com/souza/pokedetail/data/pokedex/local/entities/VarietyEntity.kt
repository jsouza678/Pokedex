package com.souza.pokedetail.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.souza.pokedetail.utils.Constants.Companion.VARIETY_TABLE_NAME

@Entity(tableName = VARIETY_TABLE_NAME)
data class VarietyEntity constructor(
    @PrimaryKey
    val _id: Int,
    val evolution_chain: String?,
    val varieties: String?,
    val color: String?,
    val description: String?
)
