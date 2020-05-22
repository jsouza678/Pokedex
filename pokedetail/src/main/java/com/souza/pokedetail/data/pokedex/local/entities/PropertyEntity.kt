package com.souza.pokedetail.data.pokedex.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.souza.pokedetail.utils.Constants.Companion.PROPERTY_TABLE_NAME

@Entity(tableName = PROPERTY_TABLE_NAME)
data class PropertyEntity constructor (
    @PrimaryKey
    var _id: Int,
    var abilities: String?,
    var name: String?,
    var height: Int?,
    var sprites: String?,
    var stats: String?,
    var types: String?,
    var weight: Int?
)
