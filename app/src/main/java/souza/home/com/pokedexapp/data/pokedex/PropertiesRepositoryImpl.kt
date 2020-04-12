package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.repository.PropertiesRepository

class PropertiesRepositoryImpl(private val id: Int, context: Context) : PropertiesRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    override val properties: LiveData<PokeProperty>?
        get() = DB_INSTANCE.propertyDao.getProperty(id)?.let {
            Transformations.map(it){ propertyObject ->
                propertyObject?.let { it1 -> PokedexMapper.propertyAsDomain(it1) }
            }
        }

    override suspend fun refreshProperties(id: Int) {
        withContext(Dispatchers.IO){
            try{
                val pokeProperty = PokeApi.retrofitService.getPokeStats(id).await()
                DB_INSTANCE.propertyDao.insertAll(PokedexMapper.propertiesAsDatabase(pokeProperty))
            }catch(e: Exception){
                Log.i("Error" , "Message From Api on Properties" + e.message)
            }
        }
    }
}
