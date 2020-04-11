package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonsDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.repository.VarietiesRepository

class VarietiesRepositoryImpl(private val id: Int, context: Context) : VarietiesRepository {

    val DB_INSTANCE = PokemonsDatabase.getDatabase(context)

    override val varieties: LiveData<PokeVariety>?
        get() = DB_INSTANCE.varietiesDao.getVariety(id)?.let {
            Transformations.map(it){ varietyObject ->
                varietyObject?.let { it1 -> PokedexMapper.variationsAsDomain(it1) }
            }
        }

    override suspend fun refreshVarieties(id: Int) {
        withContext(Dispatchers.IO){
            try{
                val pokeVariations = PokeApi.retrofitService.getVariations(id).await()
                DB_INSTANCE.varietiesDao.insertAll(PokedexMapper.variationsAsDatabase(pokeVariations))
            }catch(e: Exception){
                Log.i("teste" , "Message From Api " + e.message)
            }
        }
    }
}
