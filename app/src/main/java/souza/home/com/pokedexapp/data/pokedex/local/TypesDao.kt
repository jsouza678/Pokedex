package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import souza.home.com.pokedexapp.data.pokedex.local.model.TypeEntity
import souza.home.com.pokedexapp.utils.Constants.Companion.TYPE_TABLE_NAME

@Dao
interface TypesDao {

    @Query("SELECT * FROM $TYPE_TABLE_NAME WHERE $TYPE_TABLE_NAME._id = :pokeId")
    fun getTypeData(pokeId: Int): LiveData<TypeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg types: TypeEntity) // will pass the pokes
}
