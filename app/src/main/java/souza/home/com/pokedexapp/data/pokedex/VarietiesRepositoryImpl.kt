package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonsDatabase
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.asDatabaseModel
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
import souza.home.com.pokedexapp.domain.model.asDomainModelFromVariations

class VarietiesRepositoryImpl(private val id: String, context: Context) : VarietiesRepository{

    val DB_INSTANCE = PokemonsDatabase.getDatabase(context)

    override val varieties: LiveData<PokeVariety>?
        get() = DB_INSTANCE.varietiesDao.getVariety(id)?.let {
            Transformations.map(it){ varietyObject ->
                varietyObject?.asDomainModelFromVariations()
            }
        }

    override suspend fun refreshVarieties(id: String) {
        withContext(Dispatchers.IO){
            try{
                val pokeVariations = PokeApi.retrofitService.getVariations(id).await()
                Log.i("teste" , "Message From Api " + pokeVariations)
                DB_INSTANCE.varietiesDao.insertAll(pokeVariations.asDatabaseModel())
                Log.i("teste" , "Goes to BD: " + DB_INSTANCE.varietiesDao.getVar(Integer.parseInt(id)))
            }catch(e: Exception){
                Log.i("teste" , "Message From Api " + e.message)
            }
        }
    }
}
