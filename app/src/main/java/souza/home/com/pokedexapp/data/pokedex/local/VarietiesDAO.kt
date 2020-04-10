package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.*
import souza.home.com.pokedexapp.utils.Constants.Companion.VARIETY_TABLE_NAME
import souza.home.com.pokedexapp.domain.model.PokeVariationsEntity

@Dao
interface VarietiesDao{

    @Query("select * from $VARIETY_TABLE_NAME where $VARIETY_TABLE_NAME._poke_variety_id = :id")
    fun getVariety(id: String): LiveData<PokeVariationsEntity>?

    @Query("select * from $VARIETY_TABLE_NAME where $VARIETY_TABLE_NAME._poke_variety_id = :id")
    fun getVar(id: Int): PokeVariationsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: PokeVariationsEntity)
}
