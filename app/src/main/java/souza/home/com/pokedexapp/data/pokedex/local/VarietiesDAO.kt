package souza.home.com.pokedexapp.data.pokedex.local

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarietiesResponse
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse
import souza.home.com.pokedexapp.domain.model.PokeVariations

@Dao
interface VarietiesDao{

    @Query("select * from pokevariations where pokevariations.id = :id")
    fun getVariety(id: Int): LiveData<PokeVariations>?

    @Query("select * from pokevariations where pokevariations.id = :id")
    fun getVar(id: Int): PokeVariations?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: PokeVariations) // will pass the pokes
}

private lateinit var INSTANCE : PokeVarietiesDatabase

fun getVarietiesDatabase(context: Context): PokeVarietiesDatabase {
    synchronized(PokeVarietiesDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PokeVarietiesDatabase::class.java,
                "qqe42324234322wrietsoiooes.db").allowMainThreadQueries().build() // name
        }
    }
    return INSTANCE
}