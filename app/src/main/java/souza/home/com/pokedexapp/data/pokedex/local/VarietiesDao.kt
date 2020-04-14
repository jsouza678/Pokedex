package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import souza.home.com.pokedexapp.data.pokedex.local.model.VarietyEntity
import souza.home.com.pokedexapp.utils.Constants.Companion.VARIETY_TABLE_NAME

@Dao
interface VarietiesDao {

    @Query("select * from $VARIETY_TABLE_NAME where $VARIETY_TABLE_NAME._poke_variety_id = :id")
    fun getVariety(id: Int): LiveData<VarietyEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: VarietyEntity)
}
