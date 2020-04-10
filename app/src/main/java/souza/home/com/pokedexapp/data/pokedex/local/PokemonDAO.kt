package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.*
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_TABLE_NAME
import souza.home.com.pokedexapp.domain.model.PokemonEntity
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse

@Dao
interface PokemonDao{
    @Query("select * from $POKE_TABLE_NAME")
    fun getPokes(): LiveData<List<PokemonResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: PokemonEntity) // will pass the pokes
}
