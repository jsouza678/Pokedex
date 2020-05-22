package com.souza.pokedetail.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.souza.pokedetail.data.pokedex.local.entities.TypeEntity
import com.souza.pokedetail.utils.Constants.Companion.TYPE_TABLE_NAME

@Dao
interface TypeDao {

    @Query("SELECT * FROM $TYPE_TABLE_NAME WHERE $TYPE_TABLE_NAME._id = :id")
    fun getTypes(id: Int): LiveData<TypeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg type: TypeEntity)
}
