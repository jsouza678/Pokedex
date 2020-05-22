package com.souza.pokedetail.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.souza.pokedetail.data.pokedex.local.entities.AbilityEntity
import com.souza.pokedetail.utils.Constants.Companion.ABILITY_TABLE_NAME

@Dao
interface AbilityDao {

    @Query("SELECT * FROM $ABILITY_TABLE_NAME WHERE $ABILITY_TABLE_NAME._id = :id")
    fun getAbility(id: Int): LiveData<AbilityEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ability: AbilityEntity)
}
