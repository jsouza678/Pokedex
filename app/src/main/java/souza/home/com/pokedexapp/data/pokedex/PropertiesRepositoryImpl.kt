package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.local.PropertyDao
import souza.home.com.pokedexapp.data.pokedex.mapper.PokedexMapper
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.repository.PropertiesRepository
import souza.home.com.pokedexapp.utils.CheckNetworkState

class PropertiesRepositoryImpl(
    private val context: Context,
    private val pokedexService: PokedexService,
    private val propertyDao: PropertyDao
) : PropertiesRepository {

    private val _internet = MutableLiveData<PropertiesPokedexStatus>()

    val internet: LiveData<PropertiesPokedexStatus>
        get() = _internet

    override fun getProperties(id: Int): LiveData<PokeProperty>? {
        return propertyDao.getProperty(id)?.let {
            Transformations.map(it) { propertyObject ->
                propertyObject?.let { propertyItem -> PokedexMapper.propertyEntityAsDomainModel(propertyItem) }
            }
        }
    }

    override suspend fun refreshProperties(id: Int) {
        withContext(Dispatchers.IO) {
            if (CheckNetworkState.checkNetworkState(context)) {
                _internet.postValue(PropertiesPokedexStatus.LOADING)
                try {
                    val pokeProperty = pokedexService.fetchPokeStatsAsync(id).await()
                    propertyDao.insertAll(PokedexMapper.propertiesResponseAsDatabaseModel(pokeProperty))
                    if (pokeProperty.name.isBlank()) {
                        _internet.postValue(PropertiesPokedexStatus.EMPTY)
                    } else {
                        _internet.postValue(PropertiesPokedexStatus.DONE)
                    }
                } catch (e: Exception) {
                    _internet.postValue(PropertiesPokedexStatus.ERROR)
                    Log.i(context.getString(R.string.error_message_log), context.getString(R.string.log_error_properties) + e.message)
                }
            } else {
                _internet.postValue(PropertiesPokedexStatus.ERROR)
            }
        }
    }
}

enum class PropertiesPokedexStatus { LOADING, ERROR, DONE, EMPTY }
