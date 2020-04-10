package souza.home.com.pokedexapp.data.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokeVarietiesDatabase
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.asDatabaseModel
import souza.home.com.pokedexapp.data.pokedex.remote.model.PokeVariety
import souza.home.com.pokedexapp.domain.model.asDomainModelFromVariations
import souza.home.com.pokedexapp.domain.model.asDomainModelFromVariationsdd

class VarietiesRepositoryImpl(private val database: PokeVarietiesDatabase, private val id: Int) {

    val varieties: LiveData<PokeVariety>?
        get() = database.varietiesDao.getVariety(id)?.let {
            Transformations.map(it){
                it?.asDomainModelFromVariations()
            }
        }

    suspend fun refreshVarieties(id: String) {
        withContext(Dispatchers.IO){

            try{
                val pokeList = PokeApi.retrofitService.getVariations(id).await()
                Log.i("teste" , "Message From Api " + pokeList)
                database.varietiesDao.insertAll(pokeList.asDatabaseModel())
                Log.i("teste" , "Goes to BD: " + database.varietiesDao.getVar(Integer.parseInt(id)))
            }catch(e: Exception){
                Log.i("teste" , "Message From Api " + e.message)
            }
               //
        }
    }
}