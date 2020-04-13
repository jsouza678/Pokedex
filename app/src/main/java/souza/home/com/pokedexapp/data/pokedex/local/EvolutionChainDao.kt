package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import souza.home.com.pokedexapp.data.pokedex.local.model.EvolutionEntity
import souza.home.com.pokedexapp.utils.Constants.Companion.EVOLUTION_TABLE_NAME

@Dao
interface EvolutionChainDao {

    @Query("select * from $EVOLUTION_TABLE_NAME where $EVOLUTION_TABLE_NAME._id = :id")
    fun getEvolutionChain(id: Int): LiveData<EvolutionEntity?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg evolutionChain: EvolutionEntity)
}
