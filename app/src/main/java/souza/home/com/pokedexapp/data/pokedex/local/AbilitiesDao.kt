package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.*
import souza.home.com.pokedexapp.data.pokedex.local.model.AbilityEntity
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AllAbilitiesResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.ABILITY_TABLE_NAME

@Dao
interface AbilitiesDao{

    @Query("select * from $ABILITY_TABLE_NAME where $ABILITY_TABLE_NAME._id = :pokeId")
    fun getAbilityData(pokeId: Int): LiveData<AbilityEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg abilities: AbilityEntity)
}
