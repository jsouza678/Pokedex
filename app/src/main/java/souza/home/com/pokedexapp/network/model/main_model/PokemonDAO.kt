package souza.home.com.pokedexapp.network.model.main_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao{
    @Query("select * from databasepokedex")
    fun getPokes(): LiveData<List<DatabasePokedex>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokes: DatabasePokedex) // will pass the pokes
}

@Database(entities = [DatabasePokedex::class], version = 1)
abstract class PokemonsDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao

}
//Singleton
private lateinit var INSTANCE : PokemonsDatabase

fun getDatabase(context: Context): PokemonsDatabase{
    synchronized(PokemonsDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PokemonsDatabase::class.java,
                "pokesPlsl").allowMainThreadQueries().build() // name
            Log.i("erro", "instance initialized")
        }
    }
    return INSTANCE
}