package com.souza.pokedetail.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.souza.pokedetail.data.pokedex.local.entities.PropertyEntity
import com.souza.pokedetail.utils.Constants.Companion.PROPERTY_TABLE_NAME

@Dao
interface PropertyDao {

    @Query("SELECT * FROM $PROPERTY_TABLE_NAME WHERE $PROPERTY_TABLE_NAME._id = :id")
    fun getProperty(id: Int): LiveData<PropertyEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: PropertyEntity)
}
