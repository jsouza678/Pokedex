package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.di.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.repository.PropertiesRepository

enum class PropertiesPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PropertiesRepositoryImpl(private val id: Int, context: Context) : PropertiesRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    override val properties: LiveData<PokeProperty>?
        get() = DB_INSTANCE.propertyDao.getProperty(id)?.let {
            Transformations.map(it){ propertyObject ->
                propertyObject?.let { propertyItem -> PokedexMapper.propertyAsDomain(propertyItem) }
            }
        }

    private val _internet = MutableLiveData<PropertiesPokedexStatus>()

    val internet : LiveData<PropertiesPokedexStatus>
        get() = _internet

    override suspend fun refreshProperties(id: Int) {
        withContext(Dispatchers.IO){
            _internet.postValue(PropertiesPokedexStatus.LOADING)
            try{
                val pokeProperty = PokeApi.retrofitService.getPokeStats(id).await()
                DB_INSTANCE.propertyDao.insertAll(PokedexMapper.propertiesAsDatabase(pokeProperty))
                if(pokeProperty.name.isNullOrBlank()){
                    _internet.postValue(PropertiesPokedexStatus.EMPTY)
                }else{
                    _internet.postValue(PropertiesPokedexStatus.DONE)
                }
            }catch(e: Exception){
                _internet.postValue(PropertiesPokedexStatus.ERROR)
                Log.i("Error" , "Message From Api on Properties" + e.message)
            }
        }
    }
}