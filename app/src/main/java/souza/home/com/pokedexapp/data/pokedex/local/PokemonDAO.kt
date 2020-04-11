package souza.home.com.pokedexapp.data.pokedex.local

import androidx.lifecycle.LiveData
import androidx.room.*
import souza.home.com.pokedexapp.data.pokedex.local.model.PokemonEntity
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse
import souza.home.com.pokedexapp.utils.Constants.Companion.POKE_TABLE_NAME

@Dao
interface PokemonDao{
    @Query("SELECT * FROM $POKE_TABLE_NAME")
    fun getPokes(): LiveData<List<PokemonResponse>?>

    @Query("SELECT * FROM $POKE_TABLE_NAME WHERE $POKE_TABLE_NAME._id = :pokeId")
    fun getPokesById(pokeId: Int): LiveData<List<PokemonResponse>?>

    @Query("SELECT * FROM $POKE_TABLE_NAME WHERE $POKE_TABLE_NAME.name = :pokeName")
    fun getPokesByName(pokeName: String): LiveData<List<PokemonResponse>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: PokemonEntity) // will pass the pokes
}
