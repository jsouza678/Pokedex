package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import souza.home.com.pokedexapp.data.pokedex.local.entities.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokemonResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_TABLE_NAME

@Dao
interface PokemonDao {
    @Query("select * from $POKE_TABLE_NAME")
    fun getPokes(): LiveData<List<PokemonResponse>?>

    @Query("select * from $POKE_TABLE_NAME where $POKE_TABLE_NAME._id LIKE :pokeId||'%'")
    fun getPokesById(pokeId: Int): LiveData<List<PokemonResponse>?>

    @Query("select * from $POKE_TABLE_NAME where $POKE_TABLE_NAME.name LIKE :pokeName||'%'")
    fun getPokesByName(pokeName: String): LiveData<List<PokemonResponse>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: PokemonEntity)
}
