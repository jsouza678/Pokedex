package souza.home.com.pokedexapp.presentation.details.details_nested.stats

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import souza.home.com.pokedexapp.data.pokedex.PropertiesRepositoryImpl
import souza.home.com.pokedexapp.data.remote.PokeApi
import souza.home.com.pokedexapp.data.pokedex.remote.model.response.PropertyResponse
import souza.home.com.pokedexapp.domain.model.PokeProperty

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeStatsViewModel(pokemon: Int, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _stats = MutableLiveData<PropertyResponse>()

    val stats : LiveData<PropertyResponse>
        get() = _stats

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val propertiesRepository =
        PropertiesRepositoryImpl(pokemon, app.applicationContext)

    private val conectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork : NetworkInfo? = conectivityManager.activeNetworkInfo
    private val isConnected : Boolean = activeNetwork?.isConnected == true

    fun updatePropertiesOnViewLiveData(): LiveData<PokeProperty>? = propertiesRepository.properties

    init {
        if(isConnected){
       //     getStats(pokemon)
        }
    }

    private fun getStats(pokemon: Int) {

        _status.value = DetailsPokedexStatus.LOADING

        coroutineScope.launch {
            propertiesRepository.refreshProperties(pokemon)
            _status.value = DetailsPokedexStatus.DONE
       /*     val getStatsDeferred = PokeApi.retrofitService.getPokeStats(pokemon)
            try{
                val listResult = getStatsDeferred.await()

                _stats.value = listResult
                _status.value = DetailsPokedexStatus.DONE

            }catch(t: Throwable){
                _status.value = DetailsPokedexStatus.ERROR
            }*/
        }
    }

    override fun onCleared() {
        super.onCleared()
        Job().cancel()
    }
}