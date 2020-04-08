package souza.home.com.pokedexapp.data.pokedex.local

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse

@Dao
interface PokemonDao{
    @Query("select * from pokemon")
    fun getPokes(): LiveData<List<PokemonResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: Pokemon) // will pass the pokes
}

private lateinit var INSTANCE : PokemonsDatabase

fun getDatabase(context: Context): PokemonsDatabase {
    synchronized(PokemonsDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PokemonsDatabase::class.java,
                "pokemon").allowMainThreadQueries().build() // name
        }
    }
    return INSTANCE
}